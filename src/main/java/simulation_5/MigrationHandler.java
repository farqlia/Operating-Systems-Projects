package simulation_5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class MigrationHandler extends MigrationStrategy {

    private List<Migration> currMigrations;

    private final Predicate<Migration> continueMigrationPredicate;
    private final Predicate<Processor> startMigrationPredicate;

    public MigrationHandler(int maxLoadFactor, Predicate<Processor> startMigrationPredicate,
                            Predicate<Migration> continueMigrationPredicate) {
        super(maxLoadFactor);
        this.currMigrations = new LinkedList<>();
        this.startMigrationPredicate = startMigrationPredicate;
        this.continueMigrationPredicate = continueMigrationPredicate;
    }

    @Override
    public void startMigration(Processor processor, Process process) {
        if (startMigrationPredicate.test(processor)){
            currMigrations.add(new Migration(processor, process));
            if (PrintStatistics.print) System.out.println("[STARTED MIGRATION]"
                    + currMigrations.get(currMigrations.size() - 1));
            processor.setMigrating(true);
        } else {
            processor.addProcess(process);
        }
    }

    public void runMigrations(){
        int destinyProcessor = 0;

        Iterator<Migration> iter = currMigrations.iterator();
        Migration migration;

        while (iter.hasNext()){
            migration = iter.next();
            if (continueMigrationPredicate.test(migration)) {
                destinyProcessor = randomProcessor(migration.processor.id);
                incrementCommunications();
                if (PrintStatistics.print) System.out.println("[COMMUNICATION]: " + migration.processor.id + " <-> " + destinyProcessor + ", total communications: " + migration.currProbes);
                if (processorList.get((destinyProcessor)).currentLoad() < loadFactor) {
                    // Migrate to a processor that has feasible overload
                    processorList.get(destinyProcessor).addProcess(migration.process);
                    iter.remove();
                    incrementMigrations();
                    if (PrintStatistics.print) System.out.println("[MIGRATED SUCCESSFULLY]: " + migration.processor.id + " -> " + destinyProcessor);
                } else {
                    migration.currProbes++;
                }
            } else {
                processorList.get(migration.processor.id).addProcess(migration.process);
                if (PrintStatistics.print) System.out.println("[UNSUCCESSFUL MIGRATION]: " + migration.processor.id);
                iter.remove();
            }
        }
    }

}
