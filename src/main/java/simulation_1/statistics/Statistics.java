package simulation_1.statistics;

import simulation_1.processing.Process_;
import simulation_1.processing.Processor;
import simulation_1.schedulers.Time;

import java.util.ArrayList;

public class Statistics {

    private final Processor processor;
    private final String algName;
    private final int METRIC = 100;

    // Turnaround time is the time between the
    // arrival of the process and its completion
    private final ArrayList<Double> turnArndTimes;
    // Response time is the difference between the arrival time and first time
    // the process was visited
    private final ArrayList<Double> respTimes;

    // Waiting time is time spent by the process waiting in the queue, when it is inactive
    private final ArrayList<Double> waitingTimes;

    double averageComplTime;
    double averageRespnTime;
    double averageWaitngTime ;


    public Statistics(Processor processor, String algName){
        this.processor = processor;
        this.algName = algName;
        turnArndTimes = new ArrayList<>();
        respTimes = new ArrayList<>();
        waitingTimes = new ArrayList<>();
    }

    public void analyze(){

        Process_ process = processor.getCurrProcess();
        if (process.getCompTime() == (process.getLeftTime() + 1)){
            respTimes.add((double)((Time.get() - 1) - process.getArrTime()) / METRIC);
        }
        if (process.isTerminated()){
            double turnArndT = (double) (Time.get() - process.getArrTime()) / METRIC;
            turnArndTimes.add(turnArndT);
            waitingTimes.add(turnArndT - ((double) process.getCompTime() / METRIC));
        }

    }

    public void totalStatistics(){

        averageComplTime = (turnArndTimes.stream().reduce(Double::sum).orElse(0.0) / turnArndTimes.size()) * METRIC;
        averageRespnTime = (respTimes.stream().reduce(Double::sum).orElse(0.0) / respTimes.size()) * METRIC;
        averageWaitngTime = (waitingTimes.stream().reduce(Double::sum).orElse(0.0) / waitingTimes.size()) * METRIC;

        // What is the longest time a process had to wait
        double maxResponseTime = respTimes.stream().max(Double::compareTo).orElse(0.0) * METRIC;

        double timeThreshold =  ((double) Time.get() / (2 * METRIC));
        long numOfStarving = respTimes.stream().filter(x -> x.compareTo(timeThreshold) > 0).count();

        System.out.println("------ STATISTICS " + algName.toUpperCase() + "---------");

        System.out.println("TOTAL TIME : " + Time.get());

        System.out.println("NUMBER OF PROCESSES : " + getNumOfTerminatedPrs());

        System.out.printf("AVERAGE COMPLETION TIME: %2.2f \n", averageComplTime);

        // Percentage measurement may not be the best to compare algorithms
        System.out.printf("AVERAGE RELATIVE COMPLETION TIME: %2.2f %%\n", (averageComplTime / Time.get() * 100));

        System.out.printf("AVERAGE RESPONSE TIME: %2.2f \n", averageRespnTime);

        System.out.printf("AVERAGE RELATIVE RESPONSE TIME: %2.2f %%\n" ,(averageRespnTime / Time.get() * 100));

        System.out.printf("AVERAGE WAITING TIME:  %2.2f \n", averageWaitngTime);

        System.out.printf("AVERAGE RELATIVE WAITING TIME: %2.2f %%\n" ,(averageWaitngTime / Time.get() * 100));

        System.out.printf("MAX RESPONSE TIME: %2.2f \n", maxResponseTime);

        System.out.printf("MAX RELATIVE RESPONSE TIME: %2.2f %%\n" ,(maxResponseTime / Time.get() * 100));

        System.out.println("NUMBER OF CONTEXT SWITCHES: " + processor.getNumOfContextSwitch());

        System.out.printf("NUMBER OF STARVING %d \n", numOfStarving);

        System.out.printf("RELATIVE NUMBER OF STARVING %2.6f %%\n", ((double)numOfStarving / getNumOfTerminatedPrs()));
    }

    public int getNumOfTerminatedPrs(){
        return turnArndTimes.size();
    }

    public int getNumOfVisitedPrs(){
        return respTimes.size();
    }

    public int getNumOfPrs(){
        return getNumOfVisitedPrs() - getNumOfTerminatedPrs();
    }


    public void showStatistics(){

        System.out.println("------ STATISTICS " + algName.toUpperCase() + "---------");

        System.out.println("CURRENT = " + getNumOfPrs());

        System.out.println("VISITED = " + getNumOfVisitedPrs());

        System.out.println("TERMINATED = " + getNumOfTerminatedPrs());
        System.out.println("------------- -------------");

    }

}
