package simulation_5.migrationstrategies;

import simulation_5.main.PrintStatistics;
import simulation_5.objects.Process;
import simulation_5.objects.Processor;

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
                migration.processor.incrCommunications();
                if (PrintStatistics.print) System.out.println("[COMMUNICATION]: " + migration.processor.id + " <-> " + destinyProcessor + ", total probes: " + migration.currProbes);
                if (processorList.get((destinyProcessor)).currentLoad() < maxLoadFactor) {
                    // Migrate to a processor that has feasible overload
                    processorList.get(destinyProcessor).addProcess(migration.process);
                    processorList.get(destinyProcessor).incrInComingMigrations();
                    iter.remove();

                    incrementMigrations();
                    if (PrintStatistics.print) System.out.println("[MIGRATED SUCCESSFULLY]: " + migration.processor.id + " -> " + destinyProcessor);
                } else {
                    migration.incrProbes();
                }
            } else {
                processorList.get(migration.processor.id).addProcess(migration.process);
                if (PrintStatistics.print) System.out.println("[UNSUCCESSFUL MIGRATION]: " + migration.processor.id);
                iter.remove();
            }
        }
    }

}
