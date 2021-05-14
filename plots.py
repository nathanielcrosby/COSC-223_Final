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

meal_jobs = pd.concat([jobs1,jobs2,jobs3,jobs4,jobs5,jobs6,jobs7,jobs8],ignore_index = True)
meal_servers = pd.concat([server1,server2,server3,server4,server5,server6,server7,server8],ignore_index = True)


# Read in Shock Experiment Data
jobsb = pd.read_csv("Shock Experiment/jobs_shock_b.csv").assign(Entree = "Bad")
jobsr = pd.read_csv("Shock Experiment/jobs_shock_r.csv").assign(Entree = "Random")
jobsg = pd.read_csv("Shock Experiment/jobs_shock_g.csv").assign(Entree = "Great")

serverb = pd.read_csv("Shock Experiment/server_shock_b.csv").assign(Entree = "Bad")
serverr = pd.read_csv("Shock Experiment/server_shock_r.csv").assign(Entree = "Random")
serverg = pd.read_csv("Shock Experiment/server_shock_g.csv").assign(Entree = "Great")

shock_jobs = pd.concat([jobsb,jobsg,jobsr],ignore_index = True)
shock_servers = pd.concat([serverb,serverg,serverr],ignore_index = True)
