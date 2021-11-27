import mysql.connector


class DBConnector:
    def __init__(self, host, user, password, database):
        self.db = mysql.connector.connect(host=host, user=user, password=password, database=database)
        self.cursor = self.db.cursor()

    def populate_cb(self, cb):
        sql = "INSERT INTO CensusBlocks (id, districtId, geometryId, precinctId, isBorderBlock) VALUES(%s,%s,%s,%s,%s)"
        values = cb[['blockId', 'districtId', 'geometryId', 'precinctId', 'boundary']].values
        for value in values:
            print(value)
            value = (str(value[0]), str(value[1]), str(value[2]), str(value[3]), int(value[4]))
            print(value)
            self.cursor.execute(sql, value)
            self.db.commit()

    def populate_districts(self, districts):
        sql = "INSERT INTO Districts (id, districtingId, geometry) VALUES(%s,%s,%s)"
        values = districts[['districtId', 'districtingId', 'geometry']].values
        for value in values:
            value = (str(value[0]), str(value[1]), str(value[2]))
            self.cursor.execute(sql, value)
            self.db.commit()

    def populate_districtings(self, districtings):
        # TODO TALK TO SYLVIA TO UPDATE THE DATABASE SCHEMA FOR DISTRICTINGS
        sql = "INSERT INTO Districtings (id, stateId, populationEquality, geometricCompactness, majorityMinority, " \
              "devEnactedPopulation, devEnactedWhite,devEnactedBlack,devEnactedAsian,devEnactedDemographics," \
              "devAveragePopulation,devAverageWhite,devAverageBlack,devAverageAsian,devAverageDemographics, score)" \
              " VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        values = districtings[
            ['districtingId', 'state', 'populationEquality', 'geometricCompactness', 'majorityMinority',
             'devEnactedPopulation', 'devEnactedWhite', 'devEnactedBlack', 'devEnactedAsian',
             'devEnactedDemographics', 'devAveragePopulation', 'devAverageWhite', 'devAverageBlack',
             'devAverageAsian', 'devAverageDemographics', 'score']].values
        for value in values:
            value = (str(value[0]), str(value[1]), float(value[2]), float(value[3]), float(value[4]), float(value[5]),
                     float(value[6]), float(value[7]), float(value[8]), float(value[9]), float(value[10]),
                     float(value[11]), float(value[12]), float(value[13]), float(value[14]), float(value[15]))
            self.cursor.execute(sql, value)
            self.db.commit()

    def populate_neighbors(self, edges):
        sql = "INSERT INTO CensusBlockNeighbors (censusBlockId,neighborId) VALUES (%s, %s)"
        values = edges[['blockIdFrom', 'blockIdTo']].values
        for idx, value in enumerate(values):
            val = [(str(value[0]), str(value[1])), (str(value[1]), str(value[0]))]
            self.cursor.executemany(sql, val)
            self.db.commit()
