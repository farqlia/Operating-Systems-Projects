package simulation_2.algorithms;

public class Request {

    private final int position;
    private final int arrTime;
    private final int deadline;

    private int serviceTime;
    // 0 - normal request
    // >0 - how much time it can wait (or how many tracks)
    private int currDeadline;

    public Request(int position, int arrTime, int priority) {
        this.position = position;
        this.arrTime = arrTime;
        this.currDeadline = priority;
        this.deadline = priority;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public Request(int position, int arrTime) {
        this(position, arrTime, 0);
    }

    public void decrementDeadline(){
        currDeadline--;
    }

    public int getPosition() {
        return position;
    }

    public int getArrTime() {
        return arrTime;
    }

    public int getCurrDeadline() {
        return currDeadline;
    }

    public boolean isPriorityRequest(){
        return deadline > 0;
    }

    @Override
    public String toString() {
        return "Request{" +
                "position=" + position +
                ", arrTime=" + arrTime +
                ", deadline=" + deadline +
                ", isPriority=" + (deadline > 0) +
                '}';
    }
}
