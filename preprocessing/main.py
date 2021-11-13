import geopandas
import pandas

blocks = geopandas.read_file("zip://./precinct_data/examples/blocks.zip")
precincts = geopandas.read_file("zip://./precinct_data/examples/precincts.zip")
districts = geopandas.read_file("zip://./precinct_data/examples/districts.zip")


import maup

precincts.to_crs(districts.crs, inplace = True)
assignment = maup.assign(precincts, districts)

precincts['DISTRICT'] = assignment

from gerrychain import Graph, Partition

graph = Graph.from_json("./")
