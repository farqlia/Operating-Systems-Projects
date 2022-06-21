package simulation_5.migrationstrategies;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;
import simulation_5.objects.Process;
import simulation_5.objects.Processor;

import java.util.List;

public abstract class MigrationStrategy {

    private final RandomDataGenerator rDG = new RandomDataGenerator(new MersenneTwister(69));

    protected List<Processor> processorList;
    protected int maxLoadFactor;

    private int communicationCount = 0;
    private int migrationsCount = 0;

    public MigrationStrategy(int maxLoadFactor) {
        this.maxLoadFactor = maxLoadFactor;
    }

    public void setProcessorList(List<Processor> processorList) {
        this.processorList = processorList;
    }

    protected void incrementMigrations(){migrationsCount++;}

    protected void incrementCommunications(){communicationCount++;}

    public int getCommunicationCount() {
        return communicationCount;
    }

    public int getMigrationsCount() {
        return migrationsCount;
    }

    // Returns false until a migration is done
    public abstract void startMigration(Processor processor, Process process);

    public abstract void runMigrations();

    public int randomProcessor(int forProcessor){
        int rndProcessor;
        do {
            // Endpoints are included
            rndProcessor = rDG.nextInt(0, processorList.size() - 1);
        } while (rndProcessor == forProcessor);
        return rndProcessor;
    }

}
