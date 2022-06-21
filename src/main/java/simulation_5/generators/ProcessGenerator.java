package simulation_5.generators;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;
import simulation_5.objects.Process;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProcessGenerator {

    int numOfProcesses;
    int timeSpan = 3;
    RandomDataGenerator arrivalTimeGenerator
            = new RandomDataGenerator(new MersenneTwister(333));

    Iterable<Integer> processorsGenerator;
    Iterable<Integer> demandsGenerator;
    Iterable<Integer> durationGenerator;

    public ProcessGenerator(int numOfProcesses, Iterable<Integer> processorsGenerator,
                            Iterable<Integer> demandsGenerator, Iterable<Integer> durationGenerator) {
        this.numOfProcesses = numOfProcesses;
        this.processorsGenerator = processorsGenerator;
        this.demandsGenerator = demandsGenerator;
        this.durationGenerator = durationGenerator;
    }

    public List<Process> createProcesses(){
        List<Process> processes = new LinkedList<>();

        Iterator<Integer> demands = demandsGenerator.iterator();
        Iterator<Integer> processors = processorsGenerator.iterator();
        Iterator<Integer> duration = durationGenerator.iterator();

        int arrivalTime = 0;
        while (numOfProcesses-- > 0){
            processes.add(new Process(demands.next(), processors.next(), arrivalTime, duration.next()));
            arrivalTime += arrivalTimeGenerator.nextInt(0, timeSpan);
        }

        return processes;
    }

}
