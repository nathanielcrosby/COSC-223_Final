import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Job {
    private int startTime;
    private int arrivalTime;
    private int sizes[];
    private int departureTime;
    private int numStops = 0;
    public ArrayList<Integer> stops;
    private double waitUtility;
    private double foodUtility;
    private double[] preferences;

    public Job(int arrivalTime, int jobSize, int servers, int initIndex) {
        this.arrivalTime = arrivalTime;
        this.startTime = arrivalTime;
        sizes = new int[servers];
        this.sizes[initIndex] = jobSize;
        numStops = 1;
        stops = new ArrayList<Integer>();
        stops.add(initIndex);
        waitUtility = 0.5;
        foodUtility = 0.7;
        preferences = new double[servers];

        for(int i = 0; i<preferences.length; i++) {
            preferences[i] = Math.random()*10;
        }

    }

    public int getSizes(int index) {
        return sizes[index];
    }
    public void setSizes(int size, int index) {
        sizes[index] = size;
        numStops++;
        stops.add(index);
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int time) {
        arrivalTime = time;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public double getWaitUtility(){
        return this.waitUtility;
    }
    public double getFoodUtility(){
        return this.foodUtility;
    }

    public int getNumStops() {
        return numStops;
    }

    public int getStartTime() {
        return startTime;
    }

    public int pickServer(Server[] servers){
        /*
        int winner = stops.get(0);

        while (stops.contains(winner)) {
            winner = (int) ((Math.random() * (servers.length - 2)) + 2);
        }

        return winner;
        */
        int maxWait = 1;
        //find the longest line of any server so that we can normalize
        for (int i = 0; i < servers.length; i++){
            if (servers[i].getNumJobs() > maxWait){

                maxWait = servers[i].getNumJobs();
            }
        }
        //take weighted average of student's preferences and server stats to assign each server a 'goodness' score
        double highScore = 0;
        int winner = 0;
        double score = 0;
        //System.out.println();
        for (int i = 2; i < servers.length; i++){
            score =  (Math.pow(.5, numStops)) * foodUtility * preferences[i] - waitUtility * (10*(servers[i].getNumJobs()/maxWait));
            //System.out.println(score);
            if (score > highScore && !stops.contains(i)){
                winner = i;
                highScore = score;
            }
        }

        if(highScore <= 0) {
            winner = -1;
        }

        //return the server with the highest score
        return winner;

    }


}