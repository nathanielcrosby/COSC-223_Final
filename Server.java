import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    private Queue<Job> jobs;
    private Job currentJob;
    private int nextDepartureTime = Integer.MAX_VALUE;
    private int currTime;
    private int numjobs = 0;

    public Server() {
        jobs = new LinkedList<Job>();
    }

    public void addJob(Job job) {
        numjobs++;
        currTime = job.getArrivalTime();
        if(currentJob == null) {
            setCurrentJob(job);
            nextDepartureTime = currTime + job.getSize();
        } else {
            jobs.add(job);
        }
    }

    public void departure() {
        numjobs--;
        currTime = nextDepartureTime;
        setCurrentJob(null);
        if (!jobs.isEmpty()) {
            setCurrentJob(jobs.remove());
            nextDepartureTime = currTime + currentJob.getSize();
        } else {
            nextDepartureTime = Integer.MAX_VALUE;
        }
    }

    public void setCurrentJob(Job job) {
        currentJob = job;
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

}