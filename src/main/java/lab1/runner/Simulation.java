package lab1.runner;

import lab1.processesgenerator.Generator;
import lab1.processing.Processor;
import lab1.schedulers.*;
import lab1.statistics.Statistics;

public class Simulation {

    private final boolean showStatistics;
    private final int quantaTime;
    private final Generator processGenerator;

    private Scheduler scheduler;
    private Processor processor;
    private Statistics statistics;
    int numOfProcesses;

    public Simulation(int numOfProcesses, boolean showStatistics, int quantaTime, int minDuration,
                      int difference, int intensity, double phase, double phaseLength, int multiplicant) {
        this.showStatistics = showStatistics;
        this.numOfProcesses = numOfProcesses;
        this.quantaTime = quantaTime;
        processGenerator = new Generator(minDuration, difference, intensity, numOfProcesses, phase, phaseLength, multiplicant);
    }

    // Let the user choose some values to parametrize the simulation
    // Here we will also create process generator
    public void runSimulation(String alg){

        if ("fcfs".equals(alg)){
            runFCFS();
        } else if ("sjf".equals(alg)){
            runSJF();
        } else if ("rr".equals(alg)){
            runRR();
        } else {
            runFCFS();
            runSJF();
            runRR();
        }
    }

    private void runRR(){
        scheduler = new RR(quantaTime);
        processor = new Processor(scheduler);
        statistics = new Statistics(processor, "RR");
        run(processor, scheduler, statistics);
        clear();
    }

    private void runFCFS(){
        scheduler = new FCFS();
        processor = new Processor(scheduler);
        statistics = new Statistics(processor, "FCFS");
        run(processor, scheduler, statistics);
        clear();
    }

    private void runSJF(){
        scheduler = new SJF();
        processor = new Processor(scheduler);
        statistics = new Statistics(processor, "SJF");
        run(processor, scheduler, statistics);
        clear();
    }

    protected Generator getGenerator(Scheduler scheduler){
        return new Generator(processGenerator, scheduler);
    };

    protected void run(Processor processor, Scheduler scheduler, Statistics statistics){

        Generator gen = getGenerator(scheduler);

        gen.next();

        while (statistics.getNumOfTerminatedPrs() < numOfProcesses){

            processor.process();

            if (gen.totalGenerated() < numOfProcesses){
                gen.next();
            }

            //System.out.println(scheduler.getNumOfProcesses());

            Time.increment();

            statistics.analyze();
            if (showStatistics) {
                System.out.println("[CURRENT]: " + processor.getCurrProcess());
                statistics.showStatistics();
            }

        }

        statistics.showStatistics();
        statistics.totalStatistics();

    };

    // Reset the time
    private void clear(){
        Time.time = 0;
    }

}
