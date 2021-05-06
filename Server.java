
import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    private Queue<Job> jobs;
    private Job currentJob;
    private int nextDepartureTime = Integer.MAX_VALUE;
    private int currTime;
    private int numjobs = 0;
    private int food;
    private int itemsCapacity;
    private int itemsLeft;
    private int index;

    public Server(int index) {
        this.index = index;
        jobs = new LinkedList<Job>();
    }

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

    public Job departure() {
        numjobs--;
        currTime = nextDepartureTime;
        Job oldJob = currentJob;
        setCurrentJob(null);
        if (!jobs.isEmpty()) {
            setCurrentJob(jobs.remove());
            nextDepartureTime = currTime + currentJob.getSizes(index);
        } else {
            nextDepartureTime = Integer.MAX_VALUE;
        }
        itemsLeft --;

        return oldJob;
    }

    public void setCurrentJob(Job job) {
        currentJob = job;
    }

    public void setItemsLeft(int left){
        itemsCapacity = left;
        itemsLeft = left;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public int getNumJobs() {
        if(currentJob != null) {
            return jobs.size() + 1;
        }else
            return 0;
    }

    public int getNextDepartureTime() {
        return nextDepartureTime;
    }

    public int getFood() {
        return food;
    }

    public int getIndex() {
        return index;
    }

}