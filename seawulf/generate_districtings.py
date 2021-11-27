import sys

from read_config import read_yaml
from mggg import Seawulf

if __name__ == '__main__':
    # precincts_path, output_path, num_districtings, num_iterations, save_on, state = sys.argv[1], sys.argv[2], sys.argv[
    #     3], sys.argv[4], sys.argv[5], sys.argv[6]
    config_path = sys.argv[1]
    config = read_yaml(config_path)
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
    seawulf = Seawulf(precincts_path=precincts_path, state=state, assignment_criteria=assignment_criteria,
                      random_initial_partition=random_initial_partition, epsilon=epsilon, node_repeats=node_repeats,
                      cut_edges_multiplier=cut_edges_multiplier, population_deviation=population_deviation,
                      output_path=output_path)
    seawulf.run(num_districtings=num_districtings, iterations=iterations, save_on=save_on)
