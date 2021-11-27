import geopandas
import pandas as pd
from shapely.ops import unary_union
from gerrychain.accept import always_accept
import numpy as np
from gerrychain import (Partition, Graph, MarkovChain, updaters, constraints)
from gerrychain.proposals import recom
from functools import partial
from gerrychain import tree
import time
import yaml
import sys


class Seawulf:
    def __init__(self, precincts_path, state,
                 target_metric='population',
                 assignment_criteria='districtId', random_initial_partition=True, epsilon=0.02, node_repeats=2,
                 cut_edges_multiplier=2, population_deviation=0.1, output_path='', profiler_path=''):
        self.precincts = geopandas.read_file(precincts_path)
        self.district_list = list(self.precincts['districtId'].unique())
        self.state = state
        self.graph = Graph.from_geodataframe(self.precincts)
        self.random_initial_partition = random_initial_partition
        self.target_metric = target_metric
        self.assignment_criteria = assignment_criteria
        self.seawulf_updaters = self.create_updaters()
        self.epsilon = epsilon
        self.ideal_metric = self.precincts[self.target_metric].sum() / len(self.district_list)
        self.initial_partition = self.create_partition()
        self.proposal = self.create_proposal(node_repeats)
        self.constraints = self.create_constraints(cut_edges_multiplier, population_deviation)
        self.seawulf_districtings = pd.DataFrame(columns=['districtingId', 'state'])
        self.seawulf_districts = pd.DataFrame(
            columns=['districtId', 'districtingId', 'population', 'white', 'black', 'asian', 'democrat', 'republican',
                     'geometry'])
        self.output_path = output_path
        self.profiler_path = profiler_path

    def create_partition(self) -> Partition:
        initial_partition = Partition(graph=self.graph, assignment=self.assignment_criteria,
                                      updaters=self.seawulf_updaters)
        if self.random_initial_partition:
            random_assignment = tree.recursive_tree_part(self.graph, self.district_list, self.ideal_metric,
                                                         self.target_metric,
                                                         self.epsilon)
            initial_partition.assignment.update(random_assignment)
        return initial_partition

    def create_updaters(self) -> dict:
        seawulf_updaters = {}
        for column in self.precincts.columns:
            if column != 'geometry' and 'id' not in column.lower():
                seawulf_updaters[column] = updaters.Tally(column)
        return seawulf_updaters

    def create_proposal(self, node_repeats) -> partial:
        return partial(recom, pop_col=self.target_metric, pop_target=self.ideal_metric, epsilon=self.epsilon,
                       node_repeats=node_repeats)

    def create_constraints(self, cut_edges_multiplier, population_deviation) -> list:
        # compactness_constraint = constraints.UpperBound(
        #     lambda x: len(x['cut_edges']), 2 * len(self.initial_partition))
        population_constraint = constraints.within_percent_of_ideal_population(self.initial_partition,
                                                                               population_deviation)
        return [population_constraint]

    def process_iteration(self, partition, id) -> (pd.DataFrame, pd.DataFrame):
        precincts = []
        for node in partition.graph.nodes:
            precinctId = partition.graph.nodes[node]['precinctId']
            districtId = "".join([id, str(partition.assignment[node])])
            population = partition.graph.nodes[node]['population']
            white = partition.graph.nodes[node]['white']
            black = partition.graph.nodes[node]['black']
            asian = partition.graph.nodes[node]['asian']
            democrat = partition.graph.nodes[node]['democrat']
            republican = partition.graph.nodes[node]['republican']
            geometry = partition.graph.nodes[node]['geometry']
            precincts.append([precinctId, districtId, population, white, black, asian, democrat, republican, geometry])
        precincts = pd.DataFrame(precincts,
                                 columns=['precinctId', 'districtId', 'population', 'white', 'black', 'asian',
                                          'democrat',
                                          'republican', 'geometry'])

        districts_info = precincts[
            ['districtId', 'population', 'white', 'black', 'asian', 'democrat', 'republican']].groupby(
            'districtId').sum().reset_index()
        districts_info['districtingId'] = id
        districts_geo = precincts[['districtId', 'geometry']].groupby('districtId')['geometry'].apply(
            unary_union).reset_index()
        districts = districts_info.merge(districts_geo, on='districtId')
        districting = pd.DataFrame([[id, self.state]], columns=['districtingId', 'state'])
        return districting, districts

    def run(self, num_districtings, iterations, save_on):
        profiler_logs = []
        for i in range(num_districtings):
            chain_start = time.time()
            chain = MarkovChain(
                proposal=self.proposal,
                constraints=self.constraints,
                accept=always_accept,
                initial_state=self.initial_partition,
                total_steps=iterations)
            for new_partition in chain:
                pass  # runs iterations
            chain_end = time.time()
            id = ''.join([self.state, 'SW', str(i)])

            processing_start = time.time()
            new_districting, new_districts = self.process_iteration(new_partition, id)
            self.seawulf_districtings = self.seawulf_districtings.append(new_districting, ignore_index=True)
            self.seawulf_districts = self.seawulf_districts.append(new_districts, ignore_index=True)
            processing_end = time.time()

            storing_start = time.time()
            if (i + 1) % save_on == 0:
                self.seawulf_districtings.to_csv("".join([self.output_path, id, '_districtings.csv']), index=False)
                self.seawulf_districts.to_csv("".join([self.output_path, id, '_districts.csv']), index=False)
            storing_end = time.time()
            profiler_logs.append(
                [chain_end - chain_start, processing_end - processing_start, storing_end - storing_start])
            if (i + 1) % save_on == 0:
                logs = np.array(profiler_logs)
                mins, maxs, means, stds = logs.min(axis=0)
                maxs = logs.max(axis=0)
                means = logs.mean(axis=0)
                stds = logs.std(axis=0)
                logs = pd.DataFrame(columns=['function', 'min', 'max', 'mean', 'std'])
                logs['function'] = [f'running a chain of {iterations} iterations', 'processing districting',
                                    'saving checkpoint']
                logs['min'], logs['max'], logs['mean'], logs['std'] = mins, maxs, means, stds
                logs.to_csv(self.profiler_path)


if __name__ == '__main__':
    config_path = sys.argv[1]
    with open(config_path, 'r') as f:
        config = yaml.safe_load(f)
    precincts_path = config['precincts_path']
    state = config['state']
    num_districtings = config['num_districtings']
    iterations = config['iterations']
    assignment_criteria = config['assignment_criteria']
    random_initial_partition = config['random_initial_partition']
    epsilon = config['epsilon']
    node_repeats = config['node_repeats']
    cut_edges_multiplier = config['cut_edges_multiplier']
    population_deviation = config['population_deviation']
    output_path = config['output_path']
    save_on = config['save_on']
    profiler_path = config['profiler_path']
    seawulf = Seawulf(precincts_path=precincts_path, state=state, assignment_criteria=assignment_criteria,
                      random_initial_partition=random_initial_partition, epsilon=epsilon, node_repeats=node_repeats,
                      cut_edges_multiplier=cut_edges_multiplier, population_deviation=population_deviation,
                      output_path=output_path, profiler_path=profiler_path)
    seawulf.run(num_districtings=num_districtings, iterations=iterations, save_on=save_on)
