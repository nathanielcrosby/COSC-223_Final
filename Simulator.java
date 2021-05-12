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

    public int serverLengthPolicy(Server server1, Server server2) {
        if (server1.getNumJobs() <= server2.getNumJobs()) {
            return server1.getIndex();
        } else {
            return server2.getIndex();
        }
    }

    public int randomServerPolicy(Server server1, Server server2) {
        Random temp = new Random();
        if (temp.nextBoolean()) {
            return server1.getIndex();
        } else {
            return server2.getIndex();
        }
    }

    public double[][] Simulate(int jobs, int buffer, double p, double[] qs, int size, int numStops) {
        assert(qs.length == size);
        this.jobs = jobs;
        this.buffer = buffer;
        this.p = p;
        this.qs = qs;
        CurrTime = 0;
        totalJobs = new double[size];
        totalResponseTimes = new double[size];
        waitTimes = new ArrayList<Integer>();
        mealRatings = new ArrayList<Double>();
        int[] arrivals = new int[size];
        int[] departures = new int[size];
        Server[] servers = new Server[size];

        for (int i=0; i<size; i++) {
            totalJobs[i] = 0;
            totalResponseTimes[i] = 0;
            servers[i] = new Server(i,size,1000);
            arrivals[i] = 0;
            departures[i] = 0;
        }

        nextArr = RandomGeometric(p);

        int i = 0;
        int startingServerIndex = 0;
        int minInd;
        Server minDep;
        while (i <= jobs) {
            minInd = nextMinDepIndex(servers);
            minDep = servers[minInd];

            if (nextArr < minDep.getNextDepartureTime()) {
                CurrTime = nextArr;
                
                for(int j = 0; j<RandomGeometric(1.0/3.0);j++){
                    startingServerIndex = serverLengthPolicy(servers[0], servers[1]);

                    if ((i - this.buffer) > 0) {
                        totalJobs[startingServerIndex] += servers[startingServerIndex].getNumJobs();
                        arrivals[startingServerIndex]++;
                    }

                    servers[startingServerIndex].addJob(new Job(CurrTime, RandomGeometric(qs[startingServerIndex]), size, startingServerIndex));
                    i++;
                }
                
                nextArr = CurrTime + RandomGeometric(p);

            } else {
                CurrTime = minDep.getNextDepartureTime();

                if ((i - this.buffer) > 0) {
                    departures[minInd]++;
                    totalResponseTimes[minInd] += (CurrTime - minDep.getCurrentJob().getArrivalTime());
                }

                Job newJob = minDep.departure();
                int newInd = newJob.pickServer(servers);
                newJob.setArrivalTime(CurrTime);

                if (newInd != -1) {
                    if ((i - this.buffer) > 0) {
                        arrivals[newInd]++;
                        totalJobs[newInd] += servers[newInd].getNumJobs();
                    }
                    newJob.setSizes(RandomGeometric(qs[newInd]), newInd);
                    servers[newInd].addJob(newJob);

                } else {
                    waitTimes.add(CurrTime - newJob.getStartTime());
                    mealRatings.add(newJob.getMealRating());
                    System.out.println();
                    System.out.print("Stops: ");
                    for (int j : newJob.stops) {
                        System.out.print(j + " ");
                    }
                    System.out.println();
                }
            }

        }

        for(int j = 0; j<size; j++) {
            totalJobs[j] = totalJobs[j] / arrivals[j];
            totalResponseTimes[j] = totalResponseTimes[j] / departures[j];
        }

        return new double[][]{totalJobs, totalResponseTimes};

    }

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

    // Write Server Data to a filename.csv
    private void serverDataToCSV(String filename) throws java.io.IOException{
        double[][] results = new double[][]{totalJobs, totalResponseTimes};

        // Write the data to filename.csv
        FileWriter writer = new FileWriter("./" + filename + ".csv");
        StringBuilder sb = new StringBuilder();
        sb.append("Station 1,Station 2,Station 3,Station 4,Station 5,Station 6,Station 7,Station 8,Station 9, \n");

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

    // Write Job Data to filename.csv
    private void jobDataToCSV(String filename) throws java.io.IOException{
        double[][] results = new double[][]{totalJobs, totalResponseTimes};

        // Write the data to filename.csv
        FileWriter writer = new FileWriter("./" + filename + ".csv");
        StringBuilder sb = new StringBuilder();
        sb.append("Wait Time, Meal Ratings\n");

        for(int r = 0; r < jobs; r++){
            sb.append(Integer.toString(waitTimes.get(r)));
            sb.append(",");
            sb.append(Double.toString(Math.round(mealRatings.get(r) * 100.0) / 100.0));
            sb.append("\n");
        }

        writer.write(sb.toString());
        writer.close();
    }
}
