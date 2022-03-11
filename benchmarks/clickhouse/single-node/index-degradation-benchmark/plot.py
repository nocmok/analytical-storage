import numpy as np
import pandas
from pandas import read_csv
import matplotlib.pyplot as plt
import sys

script_dir=sys.path[0]
csv_path=script_dir + "/data.csv"
png_plot_path=script_dir + "/data.png"
dataset = read_csv(csv_path, sep=" ")

fig, axs = plt.subplots(1)
fig.set_size_inches(8,8)
axs.set_title("response time")

rows = dataset["rows"].values * 100

# min = dataset["min"].values
# line, = axs.plot(rows, min)

# max = dataset["max"].values
# line, = axs.plot(rows, max)

avg = dataset["avg"].values
line, = axs.plot(rows, avg)


plt.subplots_adjust(hspace=0.4, top=1, right=0.8)
handles, labels = axs.get_legend_handles_labels()
fig.legend(handles, labels, loc='upper right')
plt.savefig(png_plot_path, dpi=300, bbox_inches="tight", pad_inches=0.2)