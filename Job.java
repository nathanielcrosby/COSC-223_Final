import java.util.ArrayList;

public class Job {

    // Time of Arrival at Val
    private int startTime;

    // Time of Arrival at this station
    private int arrivalTime;

    // Job sizes across the stations
    private int sizes[];

    // Departure time from this station
    private int departureTime;

    // Number of stations this student has stopped at
    private int numStops = 0;

    // The stations that this student has visited
    public ArrayList<Integer> stops;

    // A measure of this student's patience for waiting in line
    private double waitUtility;

    // A measure of this student's hunger
    private double foodUtility;

    // How much this student likes the food at each of the stations
    private double[] preferences;

    // A measure of how good this student's experience at Val was (Meal & Wait time)
    private double mealRating;

    // The amount of food a student receives at a particular station
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

        // If mealQuality is not set, randomize preferences
        // Otherwise make range of 2 for meal and side quality
        if (mealQuality < 0) {
            preferences = new double[servers];
            for(int i = 0; i<preferences.length; i++) {
                preferences[i] = Math.random()*7;
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

    /**
     * Returns job size at specific server index
     * @param index index of specific server in array
     * @return int: job size at this server
     */
    public int getSizes(int index) {
        return sizes[index];
    }

    /**
     * Sets the job size at specific server.
     * @param size size of job: int
     * @param index index for server: int
     */
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

    /**
     * Grabs this student's arrival time at this station
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets this student's arrival time at this station
     */
    public void setArrivalTime(int time) {
        arrivalTime = time;
    }

    /**
     * Grabs this student's departure time at this station
     */
    public int getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets this student's departure time at this station
     */
    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Grabs this student's Wait Utility (measure of patience)
     */
    public double getWaitUtility(){
        return this.waitUtility;
    }

    /**
    * Grabs this student's Food Utility (measure of hunger)
     */
    public double getfoodUtility(){
        return this.foodUtility;
    }

    /**
    * Grabs the number of stations this student has stopped at
    */
    public int getNumStops() {
        return numStops;
    }

    /**
     * Grabs the time that this student arrived at Val
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Grabs the Meal Rating of this student's meal
     */
    public double getMealRating(){
        return mealRating;
    }

    /**
     * Decide which station to go to next (if any) by weighing each station's
     * food options against the wait time and picking the best one
     * @param servers list of stations: Server[]
     */
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

            // Calculate Utility of going to the ith station(1/2 Subject to Change)
            score = Math.pow(portionSize, numStops) * (foodUtility * preferences[i] - waitUtility * norm_waitTime);

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