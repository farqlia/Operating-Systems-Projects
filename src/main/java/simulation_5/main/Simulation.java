package simulation_5.main;

import simulation_5.generators.ProcessGenerator;
import simulation_5.migrationstrategies.MigrationStrategy;
import simulation_5.objects.Process;
import simulation_5.objects.Processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Simulation{

    private List<Processor> processors;
    private ProcessGenerator generator;
    private MigrationStrategy migrationStrategy;

    public Simulation(ProcessGenerator generator, MigrationStrategy migrationStrategy,
                      int nOfProcessors) {
        this.generator = generator;
        this.migrationStrategy = migrationStrategy;
        this.processors = new ArrayList<>();
        init(nOfProcessors);
    }

    public MigrationStrategy getMigrationStrategy() {
        return migrationStrategy;
    }

    public void run(){
        List<Process> processes = generator.createProcesses();
        while (!processes.isEmpty() || !isDone()){
            handleNextProcesses(processes);
            // First handle all migrations, then run jobs on all processors in parallel
            migrationStrategy.runMigrations();
            executeParallel();
            Time.incr();
            if (Time.getTime() % 10 == 0) {
                if (PrintStatistics.print) printInfo();
                printProcessorsOverload();
            }
        }
    }

    public class Runner implements Iterator<Void>{

        List<Process> processes;

        public Runner(){
            processes = generator.createProcesses();
        }

        public boolean hasNext(){
            return !processes.isEmpty() || !isDone();
        }

        @Override
        public Void next() {
            handleNextProcesses(processes);
            // First handle all migrations, then run jobs on all processors in parallel
            migrationStrategy.runMigrations();
            executeParallel();
            return Void.TYPE.cast(null);
        }

    }


    private void handleNextProcesses(List<Process> processes){
        Iterator<Process> iter = processes.iterator();
        Process nextProcess;

        boolean loop = true;
        while (loop && iter.hasNext()){
            nextProcess = iter.next();
            if (Time.getTime() >= nextProcess.getArrivalTime()){
                migrationStrategy.startMigration(processors.get(nextProcess.getProcessorNumber()),
                        nextProcess);

                iter.remove();
            } else if (Time.getTime() < nextProcess.getArrivalTime()) loop = false;
        }
    }

    public void printProcessorsOverload(){
        System.out.print("[TIME: " + Time.getTime() + "], ");
        System.out.println("Processors Overloads: " +
                Arrays.toString(processors.stream().mapToInt(Processor::currentLoad).toArray()));

    }

    public void printInfo(){
        System.out.println("[TIME]: " + Time.getTime());
        printProcessorsOverload();
        System.out.println("Number of migrations: " + migrationStrategy.getMigrationsCount());
        System.out.println("Number of communications: " + migrationStrategy.getCommunicationCount());
    }

    private void executeParallel(){
        for (Processor processor : processors) {
            processor.executeAll();
        }
    }

    public boolean isDone(){
        // No processor is occupied -> returns true
        return processors.parallelStream().noneMatch(Processor::isOccupied);
    }

    private void init(int nOfProcessors){
        int i = 0;
        while (nOfProcessors-- > 0) processors.add(new Processor(i++));
        this.migrationStrategy.setProcessorList(processors);
    }

    public List<Processor> getProcessors(){return processors;}

}
