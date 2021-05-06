import java.util.LinkedList;
import java.util.Queue;

public class Job {
    private int arrivalTime;
    private int sizes[];
    private int departureTime;
    private int waitUtility;
    private int foodUtility;
    private int[] preferences;

    public Job(int arrivalTime, int jobSize, int servers) {
        this.arrivalTime = arrivalTime;
        sizes = new int[servers];
        this.sizes[0] = jobSize;
    }

    public int getSizes(int index) {
        return sizes[index];
    }
    public void setSizes(int size, int index) {
        sizes[index] = size;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public int getWaitUtility(){
        return this.waitUtility;
    }
    public int getFoodUtility(){
        return this.foodUtility;
    }

    public int pickServer(Job job, Server[] servers){
        int maxWait = 0;
        //find the longest line of any server so that we can normalize
        for (int i = 0;i < servers.length; i++){
            if (servers[i].getNumJobs() > maxWait){

                maxWait = servers[i].getNumJobs();
            }
        }
        //take weighted average of student's preferences and server stats to assign each server a 'goodness' score
        int highScore = 0;
        int winner = 0;
        for (int i = 0;i < servers.length; i++){
            int score =  foodUtility * servers[i].getFood() - waitUtility * (10*(servers[i].getNumJobs()/maxWait));
            if (score > highScore){
                winner = i;
            }
        }


        //return the server with the highest score
        return winner;
    }

}