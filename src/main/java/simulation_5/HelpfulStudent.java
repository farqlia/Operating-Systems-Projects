package simulation_5;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class HelpfulStudent extends MigrationStrategy{

    private boolean shouldPerformMigrations;
    private int declareFrequency;
    private int minLoadFactor;

    public HelpfulStudent(int maxLoadFactor, int minLoadFactor, int declareFrequency) {
        super(maxLoadFactor);
        this.declareFrequency = declareFrequency;
        this.minLoadFactor = minLoadFactor;
    }

    @Override
    public boolean startMigration(Processor processor, Process process) {
        shouldPerformMigrations = true;
        return true;
    }

    private int findOverloadedProcessor(int askingProcessor){
        int processor = -1;
        Set<Integer> askedProcessors = new HashSet<>(processorList.size());
        do {
            processor = randomProcessor(askingProcessor);
            askedProcessors.add(processor);
            incrementCommunications();
            if (PrintStatistics.print) System.out.println("[COMMUNICATION]: " + askedProcessors + " <-> " + processor);

        } while (askedProcessors.size() != processorList.size() &&
                processorList.get(processor).currentLoad() < loadFactor);
        return processor;
    }

    @Override
    public void runMigrations() {
        if (shouldPerformMigrations && Time.getTime() % declareFrequency == 0){
            int overloadedProcessor;
            for (Processor processor : processorList){
                if (processor.currentLoad() <= minLoadFactor){
                    // Finds first overloaded processor and takes its process
                    overloadedProcessor = findOverloadedProcessor(processor.id);
                    if (overloadedProcessor != -1) {
                        processor.addProcess(processorList.get(overloadedProcessor).giveAwayProcess());
                        incrementMigrations();
                        if (PrintStatistics.print) System.out.println("[MIGRATED SUCCESSFULLY]: " + overloadedProcessor + " -> " + processor.id);
                    }
                }
            }
            shouldPerformMigrations = false;
        }
    }
}
