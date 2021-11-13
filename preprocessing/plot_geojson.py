import geopandas
import sys
import matplotlib.pyplot as plt

file = sys.argv[1]

df = geopandas.read_file(file)


df.plot(column='PrecCode')

plt.show()
