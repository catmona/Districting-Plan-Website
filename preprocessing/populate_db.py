import mysql.connector
import geopandas
from read_config import read_yaml

config = read_yaml('preprocessing_config.yaml')

host = config['DATABASE']['HOST']
user = config['DATABASE']['USERNAME']
password = config['DATABASE']['PASSWORD']
database_name = config['DATABASE']['DB']

enacted_cb_path = '../' +config['PREPROCESSING']['DATA']['WASHINGTON']['PROCESSED']['ENACTED']['CENSUS_BLOCKS']
enacted_cb = geopandas.read_file(enacted_cb_path)
# enacted_cb['geometry'] = enacted_cb['geometry'].apply(lambda x: str(x))
enacted_cb = enacted_cb.applymap(str)
print(enacted_cb)
values = []
for entry in enacted_cb.values:
    values.append(tuple(list(entry)))

db = mysql.connector.connect(
    host=host,
    user=user,
    password=password,
    database=database_name
)

cursor = db.cursor()

sql = "INSERT INTO TempCensusBlocks (blockId, districtId VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s,%s, %s, %s, %s)"
#
for val in values:
    cursor.execute(sql, val)
    db.commit()
    print(cursor.rowcount, ' record inserted.')