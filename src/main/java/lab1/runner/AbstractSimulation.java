package lab1.runner;

import lab1.processesgenerator.Generator;
import lab1.processing.Processor;
import lab1.schedulers.*;
import lab1.statistics.Statistics;

public abstract class AbstractSimulation {

    Scheduler schedulerFCFS;
    Processor processorFCFS;

    Scheduler schedulerSJF;
    Processor processorSJF;

    Scheduler schedulerRR;
    Processor processorRR;

    Statistics statisticsFCFS;
    Statistics statisticsSJF;
    Statistics statisticsRR;

    boolean showStatistics;

    int numOfProcesses;

    int quantaTime;


    public AbstractSimulation(int numOfProcesses, boolean showStatistics, int quantaTime) {
        this.showStatistics = showStatistics;
        this.numOfProcesses = numOfProcesses;
        this.quantaTime = quantaTime;
    }

    // Let the user choose some values to parametrize the simulation
    // Here we will also create process generator
    public void runSimulation(String alg){

        if ("fcfs".equals(alg)){
            createFCFS();
            run(processorFCFS, schedulerFCFS, statisticsFCFS);
            clear();
        } else if ("sjf".equals(alg)){
            createSJF();
            run(processorSJF, schedulerSJF, statisticsSJF);
            clear();
        } else if ("rr".equals(alg)){
            createRR();
            run(processorRR, schedulerRR, statisticsRR);
            clear();
        } else if ("all".equals(alg)) {
            createFCFS();
            run(processorFCFS, schedulerFCFS, statisticsFCFS);
            clear();
            createSJF();
            run(processorSJF, schedulerSJF, statisticsSJF);
            clear();
            createRR();
            run(processorRR, schedulerRR, statisticsRR);
            clear();
            }
    }

    private void createRR(){
        schedulerRR = new RR(quantaTime);
        processorRR = new Processor(schedulerRR, "RR");
        statisticsRR = new Statistics(processorRR, "RR");
    }

    private void createFCFS(){
        schedulerFCFS = new FCFS();
        processorFCFS = new Processor(schedulerFCFS, "FCFS");
        statisticsFCFS = new Statistics(processorFCFS, "FCFS");
    }

    private void createSJF(){
        schedulerSJF = new SJF();
        processorSJF = new Processor(schedulerSJF, "SJF");
        statisticsSJF = new Statistics(processorSJF, "SJF");
    }

    protected abstract Generator getGenerator(Scheduler scheduler);

    protected void run(Processor processor, Scheduler scheduler, Statistics statistics){

        Generator gen = getGenerator(scheduler);

        gen.next();

        while (statistics.getNumOfTerminatedPrs() < numOfProcesses){

            processor.process();

            if (gen.totalGenerated() < numOfProcesses){
                gen.next();
            }

            Time.increment();

            statistics.analyze();
            if (showStatistics) {
                System.out.println("[CURRENT]: " + processor.getCurrProcess());
                statistics.showStatistics();
            }

        }

        System.out.println("[TIME] = " + Time.get());
        statistics.showStatistics();
        statistics.totalStatistics();

    };


    // Set time to 0, maybe initialize all the variables once again with the same
    // parameters
    private void clear(){
        Time.time = 0;
    }

}
