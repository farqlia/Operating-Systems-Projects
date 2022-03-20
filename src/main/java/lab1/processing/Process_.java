package lab1.processing;

public interface Process_ {

    int getArrTime();

    double getLeftTime() ;

    int getCompTime();

    int getId();

    boolean isTerminated();

    // What part of the job has been done during 1 unit time
    // (useful when we consider context switching)
    // Value is between (0, 1>
    void doJob(double part);

}
