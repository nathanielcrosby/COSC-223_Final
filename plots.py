# matplotlib Setup
from matplotlib import pyplot as plt 
import numpy as np
plt.rcdefaults() 

# Imports
import csv 
import pandas as pd
import matplotlib.dates as mdates

# Read in Traffic Experiment Data
jobs2 = pd.read_csv("Traffic Experiment/jobs_2.csv").assign(Traffic = 2)
jobs3 = pd.read_csv("Traffic Experiment/jobs_3.csv").assign(Traffic = 3)
jobs4 = pd.read_csv("Traffic Experiment/jobs_4.csv").assign(Traffic = 4)
jobs5 = pd.read_csv("Traffic Experiment/jobs_5.csv").assign(Traffic = 5)
jobs6 = pd.read_csv("Traffic Experiment/jobs_6.csv").assign(Traffic = 6)

server2 = pd.read_csv("Traffic Experiment/server_2.csv").assign(Traffic = 2)
server3 = pd.read_csv("Traffic Experiment/server_3.csv").assign(Traffic = 3)
server4 = pd.read_csv("Traffic Experiment/server_4.csv").assign(Traffic = 4)
server5 = pd.read_csv("Traffic Experiment/server_5.csv").assign(Traffic = 5)
server6 = pd.read_csv("Traffic Experiment/server_6.csv").assign(Traffic = 6)

traffic_jobs = pd.concat([jobs2,jobs3,jobs4,jobs5,jobs6],ignore_index = True)
traffic_servers = pd.concat([server2,server3,server4,server5,server6],ignore_index = True)

line_size = traffic_servers.iloc[::2,:]
wait_times = traffic_servers.iloc[1::2,:]

'''
#Plots meal rating vs wait time at different traffic levels
fig = plt.figure(figsize=(9,9))
ax1 = fig.add_subplot(3,2,1)
ax1=plt.scatter(jobs2['Wait Time'], jobs2[' Meal Ratings'])
ax2 = fig.add_subplot(3,2,2)
ax2=plt.scatter(jobs3['Wait Time'], jobs3[' Meal Ratings'])
ax3 = fig.add_subplot(3,2,3)
ax3=plt.scatter(jobs4['Wait Time'], jobs4[' Meal Ratings'])
ax4 = fig.add_subplot(3,2,4)
ax4=plt.scatter(jobs5['Wait Time'], jobs5[' Meal Ratings'])
ax5 = fig.add_subplot(3,2,5)
ax5=plt.scatter(jobs6['Wait Time'], jobs6[' Meal Ratings'])
plt.show()

#plots line size vs traffic at all 9 stations
plt.plot(line_size['Traffic'], line_size['Station 1'], label="1")
plt.plot(line_size['Traffic'], line_size['Station 2'], label="2")
plt.plot(line_size['Traffic'], line_size['Station 3'], label="3")
plt.plot(line_size['Traffic'], line_size['Station 4'], label="4")
plt.plot(line_size['Traffic'], line_size['Station 5'], label="5")
plt.plot(line_size['Traffic'], line_size['Station 6'], label="6")
plt.plot(line_size['Traffic'], line_size['Station 7'], label="7")
plt.plot(line_size['Traffic'], line_size['Station 8'], label="8")
plt.plot(line_size['Traffic'], line_size['Station 9 '], label="9")
plt.legend()
plt.show()

#plots wait times vs traffic level at all 9 stations
plt.plot(wait_times['Traffic'], wait_times['Station 1'], label="1")
plt.plot(wait_times['Traffic'], wait_times['Station 2'], label="2")
plt.plot(wait_times['Traffic'], wait_times['Station 3'], label="3")
plt.plot(wait_times['Traffic'], wait_times['Station 4'], label="4")
plt.plot(wait_times['Traffic'], wait_times['Station 5'], label="5")
plt.plot(wait_times['Traffic'], wait_times['Station 6'], label="6")
plt.plot(wait_times['Traffic'], wait_times['Station 7'], label="7")
plt.plot(wait_times['Traffic'], wait_times['Station 8'], label="8")
plt.plot(wait_times['Traffic'], wait_times['Station 9 '], label="9")
plt.legend()
plt.show()

'''

# Read in Meal Rating Experiment Data
jobs1 = pd.read_csv("Meal Rating Experiment/jobs_1.csv").assign(entree_rating = 1)
jobs2 = pd.read_csv("Meal Rating Experiment/jobs_2.csv").assign(entree_rating = 2)
jobs3 = pd.read_csv("Meal Rating Experiment/jobs_3.csv").assign(entree_rating = 3)
jobs4 = pd.read_csv("Meal Rating Experiment/jobs_4.csv").assign(entree_rating = 4)
jobs5 = pd.read_csv("Meal Rating Experiment/jobs_5.csv").assign(entree_rating = 5)
jobs6 = pd.read_csv("Meal Rating Experiment/jobs_6.csv").assign(entree_rating = 6)
jobs7 = pd.read_csv("Meal Rating Experiment/jobs_7.csv").assign(entree_rating = 7)
jobs8 = pd.read_csv("Meal Rating Experiment/jobs_8.csv").assign(entree_rating = 8)

server1 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 1)
server2 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 2)
server3 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 3)
server4 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 4)
server5 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 5)
server6 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 6)
server7 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 7)
server8 = pd.read_csv("Meal Rating Experiment/server_3.csv").assign(entree_rating = 8)

meal_jobs = np.asarray(pd.concat([jobs1,jobs2,jobs3,jobs4,jobs5,jobs6,jobs7,jobs8],ignore_index = True))
meal_servers = pd.concat([server1,server2,server3,server4,server5,server6,server7,server8],ignore_index = True)

values = []

for i in range(8):
    values.append(3*sum(meal_jobs[meal_jobs[:,2] == i+1]) / meal_jobs[meal_jobs[:,2] == i+1].size)

values = np.asarray(values) 

plt.plot(values[:,2], values[:,0])
plt.show()
plt.plot(values[:,2], values[:,1])
plt.show()

# Read in Shock Experiment Data
jobsb = pd.read_csv("Shock Experiment/jobs_shock_b.csv").assign(Entree = "Bad")
jobsr = pd.read_csv("Shock Experiment/jobs_shock_r.csv").assign(Entree = "Random")
jobsg = pd.read_csv("Shock Experiment/jobs_shock_g.csv").assign(Entree = "Great")

serverb = pd.read_csv("Shock Experiment/server_shock_b.csv").assign(Entree = "Bad")
serverr = pd.read_csv("Shock Experiment/server_shock_r.csv").assign(Entree = "Random")
serverg = pd.read_csv("Shock Experiment/server_shock_g.csv").assign(Entree = "Great")

shock_jobs = pd.concat([jobsb,jobsg,jobsr],ignore_index = True)
shock_servers = pd.concat([serverb,serverg,serverr],ignore_index = True)
