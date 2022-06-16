package simulation_5;

public class LazyStudent extends MigrationHandler{

    public LazyStudent(int maxLoadFactor, int probes){
        super(maxLoadFactor, (m -> m.currProbes < probes));
    }
}
