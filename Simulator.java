import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;


public class Simulator {
    private int jobs;
    private double p;
    private double[] qs;
    private int nextArr;
    private int CurrTime;
    private double[] totalJobs;
    private double[] totalResponseTimes; // total time in each server
    private ArrayList<Integer> waitTimes; //total time in all servers
    private ArrayList<Double> mealRatings; 
    private int buffer;

    public Simulator() {

    }

    /**
     * Compares server1 and server2 and returns the one with the shorter line.
     * Used to compare the two card swipes.
     * @param server1
     * @param server2
     * @return
     */
    public int serverLengthPolicy(Server server1, Server server2) {
        if (server1.getNumJobs() <= server2.getNumJobs()) {
            return server1.getIndex();
        } else {
            return server2.getIndex();
        }
    }

    /**
     * The main simulate command
     *
     * @param jobs The number of jobs that will be added to the system.
     * @param buffer The number of jobs to wait before equilibrium.
     * @param p The geometric variable for when groups arrive.
     * @param qs An array of the geometric variables for job sizes at each station.
     * @param size The number of stations/
     * @param mealQuality The baseline value for how much students like the entree.
     * @param sideQuality The baseline value for how much students like the other stations.
     * @param shock A boolean for whether or not to add a shock.
     * @param traffic A geometric variable for how large the arriving group will be.
     * @param groupQueue A boolean for whether or not the groups stay together.
     * @param portionSize The fraction that the food scores are multiplied by after each stop. Simulates larger portions.
     * @return 2 arrays: the first is average line size for each server, the second is average wait time for each server
     */
    public double[][] Simulate(int jobs, int buffer, double p, double[] qs, int size, int mealQuality, int sideQuality, boolean shock, double traffic, boolean groupQueue, double portionSize) {
        assert(qs.length == size);
        this.jobs = jobs;
        this.buffer = buffer;
        this.p = p;
        this.qs = qs;
        CurrTime = 0;
        totalJobs = new double[size];
        totalResponseTimes = new double[size];

        //collects for all jobs
        waitTimes = new ArrayList<Integer>();
        mealRatings = new ArrayList<Double>();

        //number of arrivlas and departures
        int[] arrivals = new int[size];
        int[] departures = new int[size];
        Server[] servers = new Server[size];

        //create random shockpoint 50-70% into simulation.
        int shockPoint;
        if(shock) {
            shockPoint = (int) ((0.5 + Math.random() * .2) * jobs);
        } else {
            shockPoint = jobs+1;
        }

        //initialize arrays with 0
        for (int i=0; i<size; i++) {
            totalJobs[i] = 0;
            totalResponseTimes[i] = 0;
            servers[i] = new Server(i, size,1000, portionSize);
            arrivals[i] = 0;
            departures[i] = 0;
        }

        //first arrival
        nextArr = RandomGeometric(p);
        //num jobs added
        int i = 0;

        int startingServerIndex = 0;
        int minInd;
        Server minDep;
        int numNewJobs;
        while (i <= jobs) {
            //index of server with next departure
            minInd = nextMinDepIndex(servers);
            minDep = servers[minInd];

            //if job is set to arrive before one departs
            if (nextArr < minDep.getNextDepartureTime()) {
                CurrTime = nextArr;

                // doubles group sizes when in shock
                if (i < shockPoint || i > (shockPoint + 0.05*jobs)) {
                    numNewJobs = RandomGeometric(traffic);
                } else {
                    numNewJobs = RandomGeometric(traffic/2.0);
                }

                //get card swipe with smaller line
                startingServerIndex = serverLengthPolicy(servers[0], servers[1]);
                for(int j = 0; j < numNewJobs; j++){
                    //if groups don't stay together
                    if (!groupQueue)
                        startingServerIndex = serverLengthPolicy(servers[0], servers[1]);

                    if ((i - this.buffer) > 0) {
                        //track job and arrival data
                        totalJobs[startingServerIndex] += servers[startingServerIndex].getNumJobs();
                        arrivals[startingServerIndex]++;
                    }

                    //add job
                    servers[startingServerIndex].addJob(new Job(CurrTime, RandomGeometric(qs[startingServerIndex]), size, startingServerIndex, mealQuality, sideQuality, portionSize));
                    i++;
                }
                
                nextArr = CurrTime + RandomGeometric(p);

            //if next departure before next arrival
            } else {
                CurrTime = minDep.getNextDepartureTime();

                //if past buffer period and job not a dummy job (dummy jobs are when food is restocked in the servers)
                if ((i - this.buffer) > 0 && (minDep.getCurrentJob().getStartTime() != -1)) {
                    //track departures and response times for each server
                    departures[minInd]++;
                    totalResponseTimes[minInd] += (CurrTime - minDep.getCurrentJob().getArrivalTime());
                }

                //get job out of current server
                Job newJob = minDep.departure();

                //if job not a dummy job (dummy jobs are when food is restocked in the servers)
                if (newJob.getStartTime() >= 0) {
                    //job picks new server
                    int newInd = newJob.pickServer(servers);
                    newJob.setArrivalTime(CurrTime);

                    //if did not pick -1 (no desirable servers)
                    if (newInd != -1) {
                        if ((i - this.buffer) > 0) {
                            //track job and arrival data
                            arrivals[newInd]++;
                            totalJobs[newInd] += servers[newInd].getNumJobs();
                        }
                        //add job to new server with new job size
                        newJob.setSizes(RandomGeometric(qs[newInd]), newInd);
                        servers[newInd].addJob(newJob);

                        //if no desirable servers, job leaves
                    } else {
                        //track total wait time and meal quality
                        waitTimes.add(CurrTime - newJob.getStartTime());
                        mealRatings.add(newJob.getMealRating());
                    }
                }
            }

        }

        //averages num jobs and response times.
        for(int j = 0; j<size; j++) {
            totalJobs[j] = totalJobs[j] / arrivals[j];
            totalResponseTimes[j] = totalResponseTimes[j] / departures[j];
        }

        return new double[][]{totalJobs, totalResponseTimes};

    }

    /**
     * Helper function that returns server with next job set to depart.
     * @param servers The array of servers
     * @return server index
     */
    private int nextMinDepIndex(Server[] servers) {
        int min = 0;

        for (int i=0; i<servers.length; i++) {
            if ((servers[i].getNumJobs() != 0 && servers[min].getNumJobs() != 0) && (servers[i].getNextDepartureTime() <= servers[min].getNextDepartureTime())
                    && (servers[i].getNextDepartureTime() != Integer.MAX_VALUE)
                    && (servers[i].getCurrentJob().getNumStops() >= servers[min].getCurrentJob().getNumStops())) {

                min = i;

            }
        }
        return min;
    }

    /**
     * A simulation of a geometric probability.
     * @param p The probability
     * @return The number of "flips"
     */
    private static int RandomGeometric(double p) {
        int count = 0;
        double flip = 1;
        Random rand = new Random();
        while (flip > p) {
            flip = rand.nextDouble();
            count++;
        }
        return count;
    }

    /**
     * Write Server Data to a filename.csv
     *
     * @param filename Name of the file to save data too
     * @throws java.io.IOException
     */
    public void serverDataToCSV(String filename) throws java.io.IOException{
        double[][] results = new double[][]{totalJobs, totalResponseTimes};

        // Write the data to filename.csv
        FileWriter writer = new FileWriter("./" + filename + ".csv");
        StringBuilder sb = new StringBuilder();
        sb.append("Station 1,Station 2,Station 3,Station 4,Station 5,Station 6,Station 7,Station 8,Station 9 \n");

        for(int r = 0; r < 2; r++){
            for (int c = 0; c < 9; c++){
                sb.append(Double.toString(Math.round(results[r][c] * 100.0) / 100.0));
                if(c != 8) sb.append(",");
            }
            sb.append("\n");
        }

        writer.write(sb.toString());
        writer.close();
    }

    /**
     * Write Job Data to a filename.csv
     *
     * @param filename Name of the file to save data too
     * @throws java.io.IOException
     */
    public void jobDataToCSV(String filename) throws java.io.IOException{
        FileWriter writer = new FileWriter("./" + filename + ".csv");
        StringBuilder sb = new StringBuilder();
        sb.append("Wait Time, Meal Ratings\n");

        for(int r = 0; r < waitTimes.size(); r++){
            sb.append(Integer.toString(waitTimes.get(r)));
            sb.append(",");
            sb.append(Double.toString(Math.round(mealRatings.get(r) * 100.0) / 100.0));
            sb.append("\n");
        }

        writer.write(sb.toString());
        writer.close();
    }
}
