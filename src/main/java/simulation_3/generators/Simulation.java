package simulation_3.generators;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well512a;
import simulation_3.PrintStatistics;
import simulation_3.process.Process_;
import simulation_3.Time;

import java.util.List;

public class Simulation {

    private final RandomGenerator PROCESS_CHOICE = new Well512a(7);

    List<Process_> processes;
    CPU cpu;

    public Simulation(List<Process_> processes, int numOfFrames){
        this.processes = processes;
        cpu = new CPU(numOfFrames, processes);
    }


    public Simulation run(){
        int process;
        while ((process = nextProcess()) != -1){
            cpu.service(processes.get(process));
            if (PrintStatistics.print) printInfo(processes.get(process));
            Time.inc();
        }
        Time.reset();
        return this;
    }

    public void printInfo(Process_ process){

        System.out.println("[MISS COUNT]: " + process.getPagesManager().getMissCount());
        //System.out.println("[HIT COUNT]: " + process.getPagesManager().getHitCount());
        if (PrintStatistics.print){
            System.out.println("[REFERENCED PAGE]: " + process.getPage());
            if (process.getPagesManager().getEvictedPage() != null) {
                System.out.println("[EVICTED PAGE]: " + process.getPagesManager().getEvictedPage());
            }
        }

    }
/*
    private int nextProcess(){
        if (!processes.isEmpty() && !processes.get(0).hasNext()){
            System.out.println(processes.get(0).getPagesManager());
            printInfo(processes.get(0));
            processes.remove(0);
        } return processes.isEmpty() ? -1 : 0;
    }

     */
    private int nextProcess(){
        int process = PROCESS_CHOICE.nextInt(processes.size());
        while (!processes.isEmpty() && !processes.get(process).hasNext()){
            processes.remove(process);
            process = (processes.isEmpty()) ? -1 : PROCESS_CHOICE.nextInt(processes.size());
        }
        return process;
    }



}
