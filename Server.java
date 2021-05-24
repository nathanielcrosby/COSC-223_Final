import java.util.LinkedList;
import java.util.Queue;

public class Server {

    // Queue of Jobs at this server
    private Queue<Job> jobs;

    // Current Job in the server
    private Job currentJob;

    // The departure time of the job in service
    private int nextDepartureTime = Integer.MAX_VALUE;

    // Current Time
    private int currTime;

    // Capacity of servings that this station can hold
    private int itemsCapacity;

    // Number of servings left in this server
    private int itemsLeft;

    // The index associated with this server in the larger system
    private int index;

    // The number of servers in the system
    private int servers;

    // The amount of food a station gives to a job
    private double portionSize;

    public Server(int index,int servers,int capacity, double portionSize) {
        this.index = index;
        this.servers = servers;
        this.portionSize = portionSize;
        setItemsLeft(capacity);
        jobs = new LinkedList<Job>();
    }

    /**
     * Adds a job to this server
     * @param job job in the system: Job
     */
    public void addJob(Job job) {
        currTime = job.getArrivalTime();
        if(currentJob == null) {
            setCurrentJob(job);
            nextDepartureTime = currTime + job.getSizes(index);
        } else {
            jobs.add(job);
        }
    }

    /**
     * Handles the departure of a job from the server
     */
    public Job departure() {
        currTime = nextDepartureTime;
        Job oldJob = currentJob;

        setCurrentJob(null);

        //tracks items left
        if (index != 0 && index != 1) {
            itemsLeft--;
        }

        //adds dummy job if there are no items left (dummy jobs are when food is restocked in the servers)
        if ((itemsLeft == 0) && (index != 0 && index != 1)){
            setCurrentJob(new Job(-1, itemsCapacity/300, servers, index, 0, 0, 0));
            nextDepartureTime = currTime + currentJob.getSizes(index);
            itemsLeft = itemsCapacity;
        } else if (!jobs.isEmpty()) {
            setCurrentJob(jobs.remove());
            nextDepartureTime = currTime + currentJob.getSizes(index);

        } else {
            nextDepartureTime = Integer.MAX_VALUE;
        }

        return oldJob;
    }

    /**
     * Sets this server's current job
     */
    public void setCurrentJob(Job job) {
        currentJob = job;
    }

    /**
     * Sets the capacity and items left of this server
     */
    public void setItemsLeft(int left){
        itemsCapacity = (int) (left - (0.5 - portionSize)*left);
        itemsLeft = itemsCapacity;
    }

    /**
     * Grabs the current job being serviced by this server
     */
    public Job getCurrentJob() {
        return currentJob;
    }

    /**
     * Grabs the current number of jobs at the server
     */
    public int getNumJobs() {
        if(currentJob != null && currentJob.getStartTime() >= 0) {
            return jobs.size() + 1;
        } else
            return jobs.size();
    }

    /**
     * Grabs the departure time of the job currently in service
     */
    public int getNextDepartureTime() {
        return nextDepartureTime;
    }

    /**
     * Grabs the index associated with this server in the larger system
     */
    public int getIndex() {
        return index;
    }

}