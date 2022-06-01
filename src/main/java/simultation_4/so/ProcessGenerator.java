package simultation_4.so;

import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well512a;
import simulation_3.Time;
import simulation_3.generators.NormalGenerator;
import simulation_3.process.Process_;
import simulation_3.replacement_algorithms.LRU;

import java.util.ArrayList;
import java.util.List;

public class ProcessGenerator {

    // How big the process is
    private final RandomGenerator DURATION_GENERATOR = new Well512a(1);
    private final RandomGenerator PAGE_NUMBERS_GENERATOR = new MersenneTwister(1);
    private final RandomGenerator SET_SIZE_GENERATOR = new ISAACRandom(1);

    private int counter = 0;

    public Process_ next(){
        int pageNumber = 20 + PAGE_NUMBERS_GENERATOR.nextInt(20);
        return new Process_(counter, new NormalGenerator(counter++, 1000 + DURATION_GENERATOR.nextInt(1000),
                pageNumber, 2 + SET_SIZE_GENERATOR.nextInt(pageNumber / 5 - 2)),
                new LRU());
    }

    public List<Process_> createProcesses(int size){
        List<Process_> processes = new ArrayList<>();
        while (size-- > 0) processes.add(next());
        return processes;
    }

}
