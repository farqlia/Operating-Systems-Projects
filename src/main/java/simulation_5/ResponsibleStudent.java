package simulation_5;

import java.util.function.Predicate;

public class ResponsibleStudent extends MigrationHandler{

    public ResponsibleStudent(int maxLoadFactor, int probes) {
        super(maxLoadFactor, (m -> m.currProbes < probes && m.processor.currentLoad() > maxLoadFactor));
    }
}
