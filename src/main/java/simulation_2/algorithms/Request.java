package simulation_2.algorithms;

public class Request {

    private int position;
    private int arrTime;
    // 0 - normal request
    // >0 - how much time it can wait (or how many tracks)
    private int deadline;
    private boolean missedDeadline;

    public Request(int position, int arrTime, int priority) {
        this.position = position;
        this.arrTime = arrTime;
        this.deadline = priority;
    }

    public Request(int position, int arrTime) {
        this(position, arrTime, 0);
    }

    public int getPosition() {
        return position;
    }

    public int getArrTime() {
        return arrTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public boolean isPriorityRequest(){
        return deadline > 0;
    }

    public boolean isMissedDeadline(){
        return missedDeadline;
    }

    public void setMissedDeadline(boolean missedDeadline){
        this.missedDeadline = missedDeadline;
    }
}
