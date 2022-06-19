package simulation_5;

// Nie bierze pod uwagę obciążenia innych procesorów - jest statyczny
public class LazyStudent extends MigrationHandler{

    public LazyStudent(int maxLoadFactor, int probes){
        super(maxLoadFactor, (p -> true), (m -> m.currProbes < probes));
    }
}
