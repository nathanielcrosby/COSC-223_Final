import com.sun.deploy.util.ArrayUtil;

import java.util.Random;

public class Simulator {
    private int jobs;
    private double p;
    private double[] qs;
    private int nextArr;
    private int CurrTime;
    private double[] totalJobs;
    private double[] totalResponseTimes;
    private int buffer;

    public Simulator() {

    }

    public int server_length_policy(Server server1, Server server2) {
       if (server1.getCurrentJob() != null && server2.getCurrentJob() == null) {
           return server2.getIndex();
       } else if (server1.getCurrentJob() == null && server2.getCurrentJob() != null) {
           return server1.getIndex();
       } else {
           if (server1.getNumJobs() <= server2.getNumJobs()) {
               return server1.getIndex();
           } else {
               return server2.getIndex();
           }
       }
    }

    public int random_server_policy(Server server1, Server server2) {
        Random temp = new Random();
        if (temp.nextBoolean()) {
            return server1.getIndex();
        } else {
            return server2.getIndex();
        }
    }

    public double[][] Simulate(int jobs, int buffer, double p, double[] qs, int size) {
        assert(qs.length == size);
        this.jobs = jobs;
        this.buffer = buffer;
        this.p = p;
        this.qs = qs;
        CurrTime = 0;
        totalJobs = new double[size];
        totalResponseTimes = new double[size];
        int[] arrivals = new int[size];
        int[] departures = new int[size];
        Server[] servers = new Server[size];

        for (int i=0; i<size; i++) {
            totalJobs[i]=0;
            totalResponseTimes[i]=0;
            servers[i] = new Server(i);
            arrivals[i] = 0;
            departures[i] = 0;
        }

        nextArr = RandomGeometric(p);

        int i = 0;
        int starting_server_index;
        int minInd;
        Server minDep;
        while (i <= jobs) {
            minInd = nextMinDepIndex(servers);
            minDep = servers[minInd];

            if (nextArr < minDep.getNextDepartureTime()) {
                CurrTime = nextArr;
                starting_server_index = server_length_policy(servers[0], servers[1]);

                if ((i - this.buffer) > 0) {
                    totalJobs[starting_server_index] += servers[starting_server_index].getNumJobs();
                }

                servers[starting_server_index].addJob(new Job(CurrTime, RandomGeometric(qs[starting_server_index]), size));
                i++;
                nextArr = CurrTime + RandomGeometric(p);

            } else {
                CurrTime = minDep.getNextDepartureTime();

                if ((i - this.buffer) > 0) {
                    departures[minInd]++;
                    totalResponseTimes[minInd] += (CurrTime - minDep.getCurrentJob().getArrivalTime());
                }

                if (minInd < size - 1) {
                    Job newJob = minDep.departure();
                    if (minInd == 0) {
                        minInd = 1;
                    }
                    newJob.setSizes(RandomGeometric(qs[minInd+1]), minInd+1);
                    servers[minInd + 1].addJob(newJob);

                    if ((i - this.buffer) > 0) {
                        arrivals[minInd + 1]++;
                        totalJobs[minInd + 1] += servers[minInd + 1].getNumJobs();
                    }
                } else {
                    minDep.departure();
                }

            }

        }

        arrivals[0] = jobs - buffer;
        for(int j = 0; j < size; j++) {
            totalJobs[j] = totalJobs[j] / arrivals[0];
            totalResponseTimes[j] = totalResponseTimes[j] / departures[j];
        }

        return new double[][]{totalJobs, totalResponseTimes};

    }

    private int nextMinDepIndex(Server[] servers) {
        int min = 0;
        for (int i=0; i<servers.length; i++) {
            if (servers[i].getNextDepartureTime() <= servers[min].getNextDepartureTime()) {
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
}
