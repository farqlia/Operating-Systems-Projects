package simulation_5.objects;

public class Process {

    private int demand;
    private int processorNumber;
    private int arrivalTime;
    private int executionTime;

    public Process(int demand, int processorNumber, int arrivalTime, int executionTime) {
        this.demand = demand;
        this.processorNumber = processorNumber;
        this.arrivalTime = arrivalTime;
        this.executionTime = executionTime;
    }

    public boolean isDone(){return executionTime == 0;}

    public void execute(){executionTime--;}

    public int getDemand() {
        return demand;
    }

    public int getProcessorNumber() {
        return processorNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "demand=" + demand +
                ", processorNumber=" + processorNumber +
                ", arrivalTime=" + arrivalTime +
                ", executionTime=" + executionTime +
                '}';
    }
}
