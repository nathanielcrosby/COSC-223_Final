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
        int[] arrivals = new int[size];
        int[] departures = new int[size];
        Server[] servers = new Server[size];

        for (int i=0; i<size; i++) {
            totalJobs[i] = 0;
            totalResponseTimes[i] = 0;
            servers[i] = new Server(i);
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

            //System.out.println(servers[1].getNextDepartureTime());
            //System.out.println(CurrTime);

            if (nextArr < minDep.getNextDepartureTime()) {
                CurrTime = nextArr;
                startingServerIndex = serverLengthPolicy(servers[0], servers[1]);

                if ((i - this.buffer) > 0) {
                    totalJobs[startingServerIndex] += servers[startingServerIndex].getNumJobs();
                    arrivals[startingServerIndex]++;
                }

                servers[startingServerIndex].addJob(new Job(CurrTime, RandomGeometric(qs[startingServerIndex]), size, startingServerIndex));
                i++;
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

                if (newJob.getNumStops() < numStops) {
                    if ((i - this.buffer) > 0) {
                        arrivals[newInd]++;
                        totalJobs[newInd] += servers[newInd].getNumJobs();
                    }
                    newJob.setSizes(RandomGeometric(qs[newInd]), newInd);
                    servers[newInd].addJob(newJob);

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
}
