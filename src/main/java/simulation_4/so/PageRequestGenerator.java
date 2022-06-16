package simulation_4.so;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import simulation_3.process.Process_;
import simulation_3.process.State;

import java.util.List;

public class PageRequestGenerator {

    private final RandomGenerator PROCESS_CHOICE_GENERATOR = new MersenneTwister(2);
    private List<Process_> processes;

    public PageRequestGenerator(List<Process_> processes) {
        this.processes = processes;
    }

    // Randomly choose next running process to be served by cpu
    public Process_ next(){
        int i = PROCESS_CHOICE_GENERATOR.nextInt(processes.size());
        while (processes.get(i).getState() != State.RUNNING)
            i = PROCESS_CHOICE_GENERATOR.nextInt(processes.size());
        return processes.get(i);
    }

    // If there is any running process
    public boolean hasNext(){
        return processes.stream().anyMatch(x -> x.getState() == State.RUNNING);
    }

}
