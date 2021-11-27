from measures import Measures
import geopandas
import pandas as pd
import shapely
import matplotlib.pyplot as plt
import sys
import yaml

if __name__ == '__main__':
    config_path = sys.argv[1]
    with open(config_path, 'r') as f:
        config = yaml.safe_load(f)
    precincts = geopandas.read_file(config['precincts_path'])
    districts = pd.read_csv(config['districts_path'])
    districtings = pd.read_csv(config['districtings_path'])
    districts = geopandas.GeoDataFrame(districts)
    districts['geometry'] = districts['geometry'].apply(shapely.wkt.loads)
    districts.crs = precincts.crs
    enacted_districts = pd.read_csv(config['average_districts_path'])
    average_districts = pd.read_csv(config['enacted_districts_path'])
    districtings['population_equality'] = districtings.apply(lambda x: Measures.population_equality(districts[districts['districtingId'] == x['districtingId']]), axis = 1)
    districtings['geometric_compactness'] = districtings.apply(lambda x: Measures.geometric_compactness(districts[districts['districtingId'] == x['districtingId']]), axis = 1)
    districtings['majority_minority'] = districtings.apply(lambda x: Measures.majority_minority(districts[districts['districtingId'] == x['districtingId']]), axis = 1)
    districtings['dev_from_enacted_population'] = districtings.apply(lambda x: Measures.dev_population(districts[districts['districtingId'] == x['districtingId']], enacted_districts), axis = 1)
    districtings['dev_from_enacted_white'] = districtings.apply(lambda x: Measures.dev_white(districts[districts['districtingId'] == x['districtingId']], enacted_districts), axis = 1)
    districtings['dev_from_enacted_black'] = districtings.apply(lambda x: Measures.dev_black(districts[districts['districtingId'] == x['districtingId']], enacted_districts), axis = 1)
    districtings['dev_from_enacted_asian'] = districtings.apply(lambda x: Measures.dev_asian(districts[districts['districtingId'] == x['districtingId']], enacted_districts), axis = 1)
    districtings['dev_from_enacted_demographics'] = districtings.apply(lambda x: Measures.dev_demographics(districts[districts['districtingId'] == x['districtingId']], average_districts), axis = 1)
    districtings['dev_from_average_population'] = districtings.apply(lambda x: Measures.dev_population(districts[districts['districtingId'] == x['districtingId']], enacted_districts), axis = 1)
    districtings['dev_from_average_white'] = districtings.apply(lambda x: Measures.dev_white(districts[districts['districtingId'] == x['districtingId']], average_districts), axis = 1)
    districtings['dev_from_average_black'] = districtings.apply(lambda x: Measures.dev_black(districts[districts['districtingId'] == x['districtingId']], average_districts), axis = 1)
    districtings['dev_from_average_asian'] = districtings.apply(lambda x: Measures.dev_asian(districts[districts['districtingId'] == x['districtingId']], average_districts), axis = 1)
    districtings['dev_from_average_demographics'] = districtings.apply(lambda x: Measures.dev_demographics(districts[districts['districtingId'] == x['districtingId']], average_districts), axis = 1)
    districtings['score'] = districtings.apply(lambda x: Measures.objective_function(x), axis = 1)
    districtings.to_csv(config['districtings_path'])