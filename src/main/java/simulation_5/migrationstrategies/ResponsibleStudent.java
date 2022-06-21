package simulation_5.migrationstrategies;

public class ResponsibleStudent extends MigrationHandler {

    public ResponsibleStudent(int maxLoadFactor, int probes) {
        super(maxLoadFactor, (p -> p.currentLoad() > maxLoadFactor),
                (m -> m.currProbes < probes && m.processor.currentLoad() > maxLoadFactor));
    }
}
