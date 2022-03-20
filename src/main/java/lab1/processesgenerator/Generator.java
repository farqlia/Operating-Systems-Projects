package lab1.processesgenerator;

import lab1.processing.Process_;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

// Base class for all types of processes data generators
public abstract class Generator {

    protected int SCALE = 100;
    protected int lowerBound = 1;
    protected RandomGenerator NUM_GENERATOR = new Well19937c(1);
    public abstract Process_ next();
    // Number of all generated processes
    public abstract int totalGenerated();

}
