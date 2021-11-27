import numpy as np
import geopandas
import pandas as pd
import shapely

class Measures:
    @staticmethod
    def population_equality(districts) -> float:
        return 1 - (districts['population'].max() - districts['population'].min()) / districts['population'].sum()

    @staticmethod
    def geometric_compactness(districts) -> float:
        pp = 0
        for geometry in districts['geometry'].values:
            pp += 4 * np.pi * geometry.area / (geometry.length ** 2)
        pp /= len(districts)
        return pp

    @staticmethod
    def majority_minority(districts) -> float:
        mm = 0
        for index, row in districts.iterrows():
            if row['black'] > row['population'] / 2 or row['asian'] > row['population'] / 2:
                mm += 1
        return mm

    @staticmethod
    def split_county():
        pass

    @staticmethod
    def dev_population(districts, enacted):
        districts = districts.reset_index()
        enacted = enacted.reset_index()
        return 1 - sum(abs(districts['population'] - enacted['population'])) / sum(districts['population'])

    @staticmethod
    def dev_white(districts, enacted):
        districts = districts.reset_index()
        enacted = enacted.reset_index()
        return 1 - sum(abs(districts['white'] - enacted['white'])) / sum(districts['white'])

    @staticmethod
    def dev_black(districts, enacted):
        districts = districts.reset_index()
        enacted = enacted.reset_index()
        return 1 - sum(abs(districts['black'] - enacted['black'])) / sum(districts['black'])

    @staticmethod
    def dev_asian(districts, enacted):
        districts = districts.reset_index()
        enacted = enacted.reset_index()
        return 1 - sum(abs(districts['asian'] - enacted['asian'])) / sum(districts['asian'])

    @staticmethod
    def dev_demographics(districts, enacted):
        districts = districts.reset_index()
        enacted = enacted.reset_index()
        dev = (sum(abs(districts['asian'] - enacted['asian'])) / sum(districts['asian']) + sum(
            abs(districts['black'] - enacted['black'])) / sum(districts['black']) + sum(
            abs(districts['white'] - enacted['white'])) / sum(districts['white'])) / 3

        return 1 - dev

    @staticmethod
    def objective_function(districting):
        return (districting['population_equality']+ districting['geometric_compactness'] + districting['dev_from_average_demographics'])/3

