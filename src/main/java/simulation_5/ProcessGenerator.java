package simulation_5;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProcessGenerator {

    int numOfProcesses;
    int timeSpan = 10;
    RandomDataGenerator arrivalTimeGenerator
            = new RandomDataGenerator(new MersenneTwister(333));

    Iterator<Integer> processors;
    Iterator<Integer> demands;
    Iterator<Integer> duration;

    public ProcessGenerator(int numOfProcesses, Iterator<Integer> processors, Iterator<Integer> demands, Iterator<Integer> duration) {
        this.numOfProcesses = numOfProcesses;
        this.processors = processors;
        this.demands = demands;
        this.duration = duration;
    }

    public List<Process> createProcesses(){
        List<Process> processes = new LinkedList<>();

        int arrivalTime = 0;
        while (numOfProcesses-- > 0){
            processes.add(new Process(demands.next(), processors.next(), arrivalTime, duration.next()));
            arrivalTime += arrivalTimeGenerator.nextInt(0, timeSpan);
        }

        return processes;
    }

}
