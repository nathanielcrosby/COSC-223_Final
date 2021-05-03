public class Job {
    private int arrivalTime;
    private int size;
    private int departureTime;

    public Job(int arrivalTime, int size) {
        this.arrivalTime = arrivalTime;
        this.size = size;
    }

    public int getSize() {
        return size;
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
}
