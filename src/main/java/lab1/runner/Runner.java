package lab1.runner;

import lab1.processesgenerator.Generator;
import lab1.processing.Processor;
import lab1.processing.Producer;
import lab1.schedulers.*;
import lab1.statistics.Statistics;

import java.time.Duration;

public class Runner {

    /*
    Scheduler schedulerFCFS = new FCFS();
    Processor processorFCFS = new Processor(schedulerFCFS, "FCFS");

    Scheduler schedulerSJF = new SJF();
    Processor processorSJF = new Processor(schedulerSJF, "SJF");

    Scheduler schedulerRR = new RR(20);
    Processor processorRR = new Processor(schedulerRR, "RR");

    Statistics statisticsFCFS = new Statistics(schedulerFCFS, processorFCFS, "FCFS");
    Statistics statisticsSJF = new Statistics(schedulerSJF, processorSJF, "SJF");
    Statistics statisticsRR = new Statistics(schedulerRR, processorRR, "RR");

    Producer processesProducer;

    public Runner(int iterations, Generator processGenerator){
        processesProducer = new Producer(iterations, processGenerator, schedulerFCFS, schedulerSJF, schedulerRR);
    }

    public void run(){

        while(!isDone()){

            // Adds new process to all schedulers
            processesProducer.produce();

            Time.increment();
            System.out.println("[TIME] = " + Time.get());

            helper(processorFCFS, statisticsFCFS);
            helper(processorSJF, statisticsSJF);
            ((SJF) schedulerSJF).calcLongestStarvationTime();
            helper(processorRR, statisticsRR);

           try {

                Thread.sleep(Duration.ofMillis(timeScale).toMillis());

            } catch (InterruptedException ignored){}

        }



        System.out.println("[TIME] = " + Time.get());
        statisticsFCFS.totalStatistics();
        statisticsSJF.totalStatistics();
        ((SJF) schedulerSJF).getLongestStarvationTime();

        statisticsRR.totalStatistics();


    }

    public void runAlgorithm(String name){

        switch (name){
            case "RR" : runAlgorithm(processorRR, statisticsRR);
            case "FCFS" : runAlgorithm(processorFCFS, statisticsFCFS);
            case "SJF" : runAlgorithm(processorSJF, statisticsSJF);
        }

    }

    public void runAlgorithm(Processor processor, Statistics statistics){

        while (statistics.getNumOfTerminatedPrs() != iterations){
            // Adds new process to all schedulers
            // ONE THING HERE : if you want to run the algorithms one by one, make sure
            // to initialize new runner each time
            processesProducer.produce();

            Time.increment();
            System.out.println("[TIME] = " + Time.get());

            helper(processor, statistics);
        }
        try {

            Thread.sleep(Duration.ofMillis(timeScale).toMillis());

        } catch (InterruptedException ignored){}


        System.out.println("[TIME] = " + Time.get());
        statistics.totalStatistics();

    }

    public boolean isDone(){
        return statisticsFCFS.getNumOfTerminatedPrs() == iterations &&
                statisticsSJF.getNumOfTerminatedPrs() == iterations &&
                statisticsRR.getNumOfTerminatedPrs() == iterations;
    }

    public void helper(Processor p, Statistics statistics){
        if (p.process()){
            System.out.println("[PROCESSING][" + statistics.getAlgName() + "] Just processed: " + p.getCurrProcess());
            statistics.analyze();
            statistics.showStatistics();
        } else if (statistics.getNumOfTerminatedPrs() == iterations){
            System.out.println("[FINISHED][" + statistics.getAlgName());
        }
    }
    */

}
