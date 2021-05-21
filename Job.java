import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Job {

    // Time of Arrival at Val
    private int startTime;

    // Time of Arrival at this station
    private int arrivalTime;

    // ================== IDRK ==================
    private int sizes[];

    // Departure time from this station
    private int departureTime;

    // Number of stations this student has stopped at
    private int numStops = 0;

    // The stations that this student has visited (Probably dont need numStops above)
    public ArrayList<Integer> stops;

    // A measure of this student's patience for waiting in line
    private double waitUtility;

    // A measure of this student's hunger
    private double foodUtility;

    // How much this student likes the food at each of the stations
    private double[] preferences;

    // A measure of how good this student's experience at Val was (Meal & Wait time)
    private double mealRating;

    private double portionSize;


    public Job(int arrivalTime, int jobSize, int servers, int initIndex, int mealQuality, int sideQuality, double portionSize) {
        this.arrivalTime = arrivalTime;
        this.startTime = arrivalTime;
        this.portionSize = portionSize;
        sizes = new int[servers];
        this.sizes[initIndex] = jobSize;
        numStops = 1;
        stops = new ArrayList<Integer>();
        stops.add(initIndex);

        // Wait Utility random double between 0.5 - 1.0
        waitUtility = 0.5 + (0.5 * Math.random());

        // Food Utility random double between 0.5 - 1.0
        foodUtility = 0.5 + (0.5 * Math.random());

        if (mealQuality < 0) {
            preferences = new double[servers];
            for(int i = 0; i<preferences.length; i++) {
                preferences[i] = Math.random()*10;
            }
        } else {
            preferences = new double[servers];
            for(int i = 0; i<preferences.length; i++) {
                preferences[i] = sideQuality + (2 * Math.random());
            }
            preferences[2] = mealQuality + (2 * Math.random());
            preferences[3] = mealQuality + (2 * Math.random());

        }

    }

    // ================== IDRK ==================
    public int getSizes(int index) {
        return sizes[index];
    }

    // ================== IDRK ==================
    public void setSizes(int size, int index) {
        sizes[index] = size;
        numStops++;
        if (index == 2 || index == 3) {
            stops.add(2);
            stops.add(3);
        } else {
            stops.add(index);
        }
    }

    // Grab this student's arrival time at this station
    public int getArrivalTime() {
        return arrivalTime;
    }

    // Set this student's arrival time at this station
    public void setArrivalTime(int time) {
        arrivalTime = time;
    }

    // Grab this student's departure time at this station
    public int getDepartureTime() {
        return departureTime;
    }

    // Set this student's departure time at this station
    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    // Grab this student's Wait Utility (measure of patience)
    public double getWaitUtility(){
        return this.waitUtility;
    }

    // Grab this student's Food Utility (measure of hunger)
    public double getfoodUtility(){
        return this.foodUtility;
    }

    // Grab the number of stations this student has stopped at
    public int getNumStops() {
        return numStops;
    }

    // Grab the time that this student arrived at Val
    public int getStartTime() {
        return startTime;
    }

    // Grab the Meal Rating of this student's meal
    public double getMealRating(){
        return mealRating;
    }

    // Decide which station to go to next (if any) by weighing each station's
    // food options against the wait time and picking the best one
    public int pickServer(Server[] servers){

        // Find the server with longest wait (for normalizing)
        int maxWait = 0;
        for (int i = 0; i < servers.length; i++){
            if (servers[i].getNumJobs() > maxWait){
                maxWait = servers[i].getNumJobs();
            }
        }

        // Determine the best station (if any) to go to next
        double highScore = 0;
        int winner = 0;
        double score = 0;
        double norm_waitTime = 0;

        for (int i = 2; i < servers.length; i++){

            // Calculate Normalized Wait Time out of 10 for the ith station
            if (maxWait == 0){
                norm_waitTime = 0;
            } else{
                norm_waitTime = (10*(servers[i].getNumJobs()/maxWait));
            }

            // Calculate Utilty of going to the ith station(1/2 Subject to Change)
            score = Math.pow(portionSize, numStops) * (foodUtility * preferences[i] - waitUtility * norm_waitTime);

            //System.out.println(score);

            // Keep track of the best station and the score of that station
            if (score > highScore && !stops.contains(i)){
                winner = i;
                highScore = score;
            }
        }

        // If remaining stations are not worth waiting in, go sit down
        if(highScore < 0.5) {
            winner = -1;
        }

        // Add the utility of going to this station to mealRating
        mealRating += highScore;

        //return the server with the highest score
        return winner;
    }
}