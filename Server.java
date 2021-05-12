
import java.util.AbstractQueue;
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

    // Number of jobs in the system
    private int numjobs = 0;

    // ================== IDRK ==================
    private int food;

    // Capacity of servings that this station can hold 
    private int itemsCapacity;

    // Number of servings left in this server
    private int itemsLeft;

    // The index associated with this server in the larger system
    private int index;

    // The number of servers in the system
    private int servers;

    public Server(int index,int servers,int capacity) {
        this.index = index;
        this.servers = servers;
        setItemsLeft(capacity);
        jobs = new LinkedList<Job>();
    }

    // Add a job to this server
    public void addJob(Job job) {
        if (itemsLeft == 0){
            currTime += 10;
            itemsLeft = itemsCapacity;
        }

        numjobs++;
        currTime = job.getArrivalTime();
        if(currentJob == null) {
            setCurrentJob(job);
            nextDepartureTime = currTime + job.getSizes(index);
        } else {
            jobs.add(job);
        }
    }

    // Handle the departure of a job from the server
    public Job departure() {
        numjobs--;
        currTime = nextDepartureTime;
        Job oldJob = currentJob;

        setCurrentJob(null);
        itemsLeft --;
        
        /*
        if (itemsLeft == 0 && (index != 0 || index != 1)){
            setCurrentJob(new Job(-1, itemsCapacity/200, servers, index));
        }
        */


        if (!jobs.isEmpty()) {
            setCurrentJob(jobs.remove());
            nextDepartureTime = currTime + currentJob.getSizes(index);
        } else {
            nextDepartureTime = Integer.MAX_VALUE;
        }

        return oldJob;
    }

    // Set this server's current job
    public void setCurrentJob(Job job) {
        currentJob = job;
    }

    // Set the capacity and items left of this server
    public void setItemsLeft(int left){
        itemsCapacity = left;
        itemsLeft = left;
    }

    // Grab the current job being serviced by this server
    public Job getCurrentJob() {
        return currentJob;
    }

    // Grab the number of jobs at the server right now
    public int getNumJobs() {
        if(currentJob != null) {
            return jobs.size() + 1;
        } else
            return 0;
    }

    // Grab the departure time of the job in service
    public int getNextDepartureTime() {
        return nextDepartureTime;
    }

    // ================== IDRK ==================
    public int getFood() {
        return food;
    }

    // Grab the index associated with this server in the larger system
    public int getIndex() {
        return index;
    }

}