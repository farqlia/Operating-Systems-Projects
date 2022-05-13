package simulation_3.generators;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well512a;
import simulation_3.Process_;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final RandomGenerator PROCESS_CHOICE = new Well512a(7);

    List<Process_> processes;
    CPU cpu;

    public Simulation(){
        processes = new ArrayList<>();
        cpu = new CPU();
    }

    public void run(){
        int process;
        while ((process = nextProcess()) != -1){
            cpu.service(processes.get(process));
        }
    }

    private int nextProcess(){
        int process = PROCESS_CHOICE.nextInt(processes.size());
        while (!processes.isEmpty() && !processes.get(process).hasNext()){
            processes.remove(process);
            process = (processes.isEmpty()) ? -1 : PROCESS_CHOICE.nextInt(processes.size());
        }
        return process;
    }

}
