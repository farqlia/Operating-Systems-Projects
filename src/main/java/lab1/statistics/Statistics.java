package lab1.statistics;

import lab1.processing.Process_;
import lab1.processing.Processor;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;

import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;

public class Statistics {

    private Scheduler scheduler;
    private Processor processor;
    private String algName;
    private int METRIC = 100;
    // Turnaround time is the time between the
    // arrival of the process and it's completion
    // Change it to mapping
    private ArrayList<Double> turnArndTimes;
    // Response time is the difference between the arrival time and first time
    // the process was visited
    private ArrayList<Double> respTimes;

    // Waiting time is time spent by the process waiting in the queue, when it is inactive
    private ArrayList<Double> waitingTimes;

    double averageComplTime;
    double averageRespnTime;
    double averageWaitngTime ;

    public String getAlgName() {
        return algName;
    }

    public Statistics(Scheduler scheduler, Processor processor, String algName){
        this.scheduler = scheduler;
        this.processor = processor;
        this.algName = algName;
        turnArndTimes = new ArrayList<>();
        respTimes = new ArrayList<>();
        waitingTimes = new ArrayList<>();
    }

    public void analyze(){

        Process_ process = processor.getCurrProcess();
        if (process != null) {
            if (process.getCompTime() == (process.getLeftTime() + 1)){
                respTimes.add((double)((Time.get() - 1) - process.getArrTime()) / METRIC);
            }

            if (process.isTerminated()){
                double turnArndT = (double) (Time.get() - process.getArrTime()) / METRIC;
                turnArndTimes.add(turnArndT);
                waitingTimes.add(turnArndT - ((double) process.getCompTime() / METRIC));
            }
        }

    }

    public void totalStatistics(){

        averageComplTime = (turnArndTimes.stream().reduce(Double::sum).orElse(0.0) / turnArndTimes.size()) * METRIC;
        averageRespnTime = (respTimes.stream().reduce(Double::sum).orElse(0.0) / respTimes.size()) * METRIC;
        averageWaitngTime = (waitingTimes.stream().reduce(Double::sum).orElse(0.0) / waitingTimes.size()) * METRIC;

        // What is the longest time a process had to wait
        double maxResponseTime = respTimes.stream().max(Double::compareTo).orElse(0.0) * METRIC;

        System.out.println("------ STATISTICS " + algName.toUpperCase() + "---------");

        System.out.println("TOTAL TIME : " + Time.get());

        System.out.println("NUMBER OF PROCESSES : " + getNumOfTerminatedPrs());

        System.out.printf("AVERAGE COMPLETION TIME: %2.2f \n", averageComplTime);

        System.out.printf("AVERAGE RELATIVE COMPLETION TIME: %2.2f %%\n", (averageComplTime / Time.get() * 100));

        System.out.printf("AVERAGE RESPONSE TIME: %2.2f \n", averageRespnTime);

        System.out.printf("AVERAGE RELATIVE RESPONSE TIME: %2.2f %%\n" ,(averageRespnTime / Time.get() * 100));

        System.out.printf("AVERAGE WAITING TIME:  %2.2f \n", averageWaitngTime);

        System.out.printf("AVERAGE RELATIVE WAITING TIME: %2.2f %%\n" ,(averageWaitngTime / Time.get() * 100));

        System.out.printf("MAX RESPONSE TIME: %2.2f \n", maxResponseTime);

        System.out.printf("MAX RELATIVE RESPONSE TIME: %2.2f %%\n" ,(maxResponseTime / Time.get() * 100));

        System.out.println("NUMBER OF CONTEXT SWITCHES: " + processor.getNumOfContextSwitch());
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

        System.out.println("CURRENT = " + processor.getNumOfPrs());

        System.out.println("VISITED = " + getNumOfVisitedPrs());

        System.out.println("TERMINATED = " + getNumOfTerminatedPrs());
        System.out.println("------------- -------------");

    }

}
