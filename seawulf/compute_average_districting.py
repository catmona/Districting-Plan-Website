from measures import Measures
import geopandas
import pandas as pd
import shapely
import matplotlib.pyplot as plt
import yaml
import sys

if __name__ == '__main__':
    config_path = sys.argv[1]
    with open(config_path, 'r') as f:
        config = yaml.safe_load(f)
    districts = pd.read_csv(config['districts_path'])
    districtings = pd.read_csv(config['districtings_path'])

    avg_districts = []
    for i in range(len(districts[districts['districtingId'] == districtings.iloc[0]['districtingId']])):
        avg_districts.append(
            [''.join([config['average_districting_id'], str(i)]), config['average_districting_id'], 0, 0, 0, 0, 0, 0])

    avg_districts = pd.DataFrame(avg_districts,
                                 columns=['districtId', 'districtingId', 'population', 'white', 'black', 'asian',
                                          'democrat', 'republican'])

    for districtingId in districtings['districtingId'].unique():
        current_districts = districts[districts['districtingId'] == districtingId].reset_index()
        avg_districts['population'] = avg_districts['population'] + current_districts['population'] / len(districtings)
        avg_districts['white'] = avg_districts['white'] + current_districts['white'] / len(districtings)
        avg_districts['black'] = avg_districts['black'] + current_districts['black'] / len(districtings)
        avg_districts['asian'] = avg_districts['asian'] + current_districts['asian'] / len(districtings)
        avg_districts['democrat'] = avg_districts['democrat'] + current_districts['democrat'] / len(districtings)
        avg_districts['republican'] = avg_districts['republican'] + current_districts['republican'] / len(districtings)

    avg_districts.to_csv(config['average_districts_path'])
