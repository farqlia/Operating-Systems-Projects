package simulation_1.processing;

public class SimpleProcess implements Process_ {

    private final int id;
    private final int arrTime;
    private final int compTime;
    private int leftTime;

    public SimpleProcess(int id, int arrTime, int completionTime) {
        this.id = id;
        this.arrTime = arrTime;
        this.compTime = completionTime;
        this.leftTime = completionTime;
    }

    public SimpleProcess(SimpleProcess other){
        this.id = other.id;
        this.arrTime = other.arrTime;
        this.compTime = other.compTime;
        this.leftTime = other.leftTime;
    }

    public void doJob(){
        leftTime-= 1;
    }

    public boolean isTerminated() {return leftTime <= 0;}

    public int getArrTime() {
        return arrTime;
    }

    public double getLeftTime() {
        return leftTime;
    }

    public int getCompTime() {
        return compTime;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SimpleProcess{" +
                "id=" + id +
                ", arrTime=" + arrTime +
                ", compTime=" + compTime +
                ", leftTime=" + leftTime +
                '}';
    }
}
