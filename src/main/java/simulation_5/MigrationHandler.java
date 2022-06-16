package simulation_5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class MigrationHandler extends MigrationStrategy {

    private List<Migration> currMigrations;

    private final Predicate<Migration> continueMigrationPredicate;

    public MigrationHandler(int maxLoadFactor, Predicate<Migration> continueMigrationPredicate) {
        super(maxLoadFactor);
        this.currMigrations = new LinkedList<>();
        this.continueMigrationPredicate = continueMigrationPredicate;
    }

    @Override
    public boolean startMigration(Processor processor, Process process) {
        currMigrations.add(new Migration(processor, process));
        if (PrintStatistics.print) System.out.println("[STARTED MIGRATION]" + currMigrations.get(currMigrations.size() - 1));
        processor.setMigrating(true);
        return true;
    }

    public void runMigrations(){
        int destinyProcessor = 0;

        Iterator<Migration> iter = currMigrations.iterator();
        Migration migration;

        while (iter.hasNext()){
            migration = iter.next();
            if (continueMigrationPredicate.test(migration)) {
                destinyProcessor = randomProcessor(migration.processor.id);
                if (PrintStatistics.print) System.out.println("[COMMUNICATION]: " + migration.processor.id + " <-> " + destinyProcessor + ", total communications: " + migration.currProbes);
                if (processorList.get((destinyProcessor)).currentLoad() < loadFactor) {
                    // Migrate to a processor that has feasible overload
                    processorList.get(destinyProcessor).addProcess(migration.process);
                    iter.remove();
                    incrementMigrations();
                    if (PrintStatistics.print) System.out.println("[MIGRATED SUCCESSFULLY]: " + migration.processor.id + " -> " + destinyProcessor);
                } else {
                    migration.currProbes++;
                    incrementCommunications();
                }
            } else {
                processorList.get(migration.processor.id).addProcess(migration.process);
                if (PrintStatistics.print) System.out.println("[UNSUCCESSFUL MIGRATION]: " + migration.processor.id);
                iter.remove();
            }
        }
    }

}
