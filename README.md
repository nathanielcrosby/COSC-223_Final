# COSC-223_Final
Final Project for COSC-223

Matthew Kaneb, David Dang, William DeGroot, and Nathaniel Crosby

The simulation is broken into 4 java classes.

Main():
This is where the various experiments are laid out and called. 
We first create an instance of the Simulator class.
Then the parameters are set and the simulation begins using the Simulator.simulate() command.
The data is then saved to .csv files in folders named for eahc group.

Simulator():
This is the core simulator class. 
It creates an array of servers. 
The first 2 are the card swipe, the next 2 are entree, the last is pizza and the rest are salad, pasta, sandwiches and lighter side.
It then runs through the simulation of the queueing system where jobs are added, go to different stations and then leave.
Built into this class are the save csv functions.
 
*Simulate()*: The main simulate command
- jobs: The number of jobs that will be added to the system.
- buffer: The number of jobs to wait before equilibrium.
- p: The geometric variable for when groups arrive.
- qs: An array of the geometric variables for job sizes at each station.
- size: The number of stations/
- mealQuality: The baseline value for how much students like the entree.
- sideQuality: The baseline value for how much students like the other stations.
- shock: A boolean for whether or not to add a shock.
- traffic: A geometric variable for how large the arriving group will be.
- groupQueue: A boolean for whether or not the groups stay together.
- portionSize: The fraction that the food scores are multiplied by after each stop. Simulates larger portions.
- return 2 arrays: the first is average line size for each server, the second is average wait time for each server
   

Server():
This represents an individual server. Jobs can enter and depart. It maintains a FIFO queue of jobs and runs through them.
It also tracks the amount of food left and adds more when needed.

Job():
This represents a single Job (Person eating). It tracks the individual's preferences, hunger, patience, total wait time, job sizes at each station and picks the next station to go to.
