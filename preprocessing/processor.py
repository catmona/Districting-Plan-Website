import geopandas
from gerrychain import Graph
import pandas as pd
import shapely


class Processor:
    @staticmethod
    def compute_border_blocks(cb) -> geopandas.GeoDataFrame:
        graph = Graph.from_geodataframe(cb)
        boundary_blocks = {}
        for edge in graph.edges:
            nodeFrom = graph.nodes[edge[0]]
            nodeTo = graph.nodes[edge[1]]
            if nodeFrom['districtId'] != nodeTo['districtId']:
                boundary_blocks[nodeFrom['blockId']] = 1
                boundary_blocks[nodeTo['blockId']] = 1

        cb['boundary'] = cb['blockId'].apply(lambda x: 1 if x in boundary_blocks.keys() else 0)
        return cb

    @staticmethod
    def get_edges(gdf) -> pd.DataFrame:
        graph = Graph.from_geodataframe(gdf)
        edges = []
        for (i, j) in graph.edges:
            blockIdFrom = graph.nodes[i]['blockId']
            blockIdTo = graph.nodes[j]['blockId']
            edges.append([blockIdFrom, blockIdTo])
        edges = pd.DataFrame(edges, columns=['blockIdFrom', 'blockIdTo'])
        return edges

    @staticmethod
    def get_polygons(geometry) -> list:
        polygons = []
        if type(geometry) == shapely.geometry.MultiPolygon:
            for shape in geometry:
                polygons.extend(Processor.get_polygons(shape))
        elif type(geometry) == shapely.geometry.Polygon:
            polygons.append(geometry)
        return polygons

    @staticmethod
    def fix_geometry(precincts) -> geopandas.GeoDataFrame:
        rows = precincts.values
        processed_precincts = []
        id = 0
        for row in rows[:100]:
            countyId = row[0]
            precinctName = row[1]
            polygons = Processor.get_polygons(row[2])
            for polygon in polygons:
                processed_precincts.append([countyId, precinctName, id, polygon])
                id += 1
        processed_precincts = geopandas.GeoDataFrame(
            pd.DataFrame(processed_precincts, columns=['countyId', 'precinctName', 'precinctId', 'geometry']))
        processed_precincts.crs = precincts.crs
        return processed_precincts
