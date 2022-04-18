package simulation_2.algorithms;

public class Request {

    private final int position;
    private final int arrTime;
    private int serviceTime;
    // 0 - normal request
    // >0 - how much time it can wait (or how many tracks)
    private int deadline;
    private final boolean isPriority;
    private boolean missedDeadline;

    public Request(int position, int arrTime, int priority) {
        this.position = position;
        this.arrTime = arrTime;
        this.deadline = priority;
        this.isPriority = (deadline > 0);
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
        deadline--;
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
        return isPriority;
    }

    public boolean isMissedDeadline(){
        return missedDeadline;
    }

    public void setMissedDeadline(boolean missedDeadline){
        this.missedDeadline = missedDeadline;
    }

    @Override
    public String toString() {
        return "Request{" +
                "position=" + position +
                ", arrTime=" + arrTime +
                ", deadline=" + deadline +
                ", isPriority=" + isPriority +
                ", missedDeadline=" + missedDeadline +
                '}';
    }
}
