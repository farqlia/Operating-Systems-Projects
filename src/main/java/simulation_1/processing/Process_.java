package simulation_1.processing;

public interface Process_ {

    int getArrTime();

    double getLeftTime() ;

    int getCompTime();

    int getId();

    boolean isTerminated();

    void doJob();

}
