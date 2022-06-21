package simulation_5.migrationstrategies;

import simulation_5.main.PrintStatistics;
import simulation_5.main.Time;
import simulation_5.objects.Process;
import simulation_5.objects.Processor;


import java.io.PrintStream;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelpfulStudent extends MigrationStrategy {

    private int declareFrequency;
    private int minLoadFactor;

    public HelpfulStudent(int maxLoadFactor, int minLoadFactor, int declareFrequency) {
        super(maxLoadFactor);
        this.declareFrequency = declareFrequency;
        this.minLoadFactor = minLoadFactor;
    }

    @Override
    public void startMigration(Processor processor, Process process) {
        processor.addProcess(process);
        if (PrintStatistics.print) System.out.println("[NEW PROCESS]: " + process);
    }

    private int findOverloadedProcessor(int askingProcessor){
        int processor;
        Set<Integer> toBeAskedProcessors =
                IntStream.range(0, processorList.size()).boxed().collect(Collectors.toSet());

        toBeAskedProcessors.remove(askingProcessor);
        if (PrintStatistics.print) System.out.println("[COMMUNICATION]: ");
        do {

            processor = randomProcessor(askingProcessor);
            if (toBeAskedProcessors.remove(processor)){
                processorList.get(askingProcessor).incrCommunications();
                if (PrintStatistics.print)
                    System.out.print(askingProcessor + " <-> " + processor + ", ");
            }
        } while (!toBeAskedProcessors.isEmpty() && processorList.get(processor).currentLoad() < maxLoadFactor);
        System.out.println();
        return processorList.get(processor).currentLoad() >= maxLoadFactor ? processor : -1;
    }

    @Override
    public void runMigrations() {

        if (Time.getTime() % declareFrequency == 0){
            int overloadedProcessor;
            Process process;
            for (Processor processor : processorList){
                // Finds first overloaded processor and takes its process
                if (processor.currentLoad() <= minLoadFactor &&
                        (overloadedProcessor = findOverloadedProcessor(processor.id)) != -1){
                    processor.addProcess((process = processorList.get(overloadedProcessor)
                            .giveAwayProcess()));
                    processor.incrInComingMigrations();
                    processorList.get(overloadedProcessor).incrOutGoingMigrations();
                    if (PrintStatistics.print) System.out.println("[MIGRATED SUCCESSFULLY]: "
                            + overloadedProcessor + " -> " + processor.id + " " + process);
                }
            }

        }
    }
}
