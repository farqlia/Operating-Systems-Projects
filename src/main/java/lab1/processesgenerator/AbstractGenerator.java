package lab1.processesgenerator;

import lab1.schedulers.Scheduler;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public abstract class AbstractGenerator extends Generator{

    int minDuration;
    // max difference between the length of processes
    int difference;
    double threshold;
    double access;
    // Let the chance raise for the next process
    double chance;
    // How many processes can enter at the same time
    int intensity;
    int numOfProcesses;
    RandomGenerator ACCESS_GENERATOR = new Well19937c(1);
    RandomGenerator NUM_GENERATOR = new Well19937c(2);
    RandomGenerator INTENSITY_GENERATOR = new Well19937c(3);

    Scheduler[] schedulers;

    int counter;

    public AbstractGenerator(int minDuration, int difference, int intensity, double threshold,
                            int numOfProcesses, double chance, Scheduler... schedulers) {
        this.minDuration = minDuration;
        this.difference = difference;
        this.intensity = intensity;
        this.threshold = threshold;
        this.numOfProcesses = numOfProcesses;
        this.access = 1;
        this.chance = chance;
        this.schedulers = schedulers;
    }

    public AbstractGenerator(AbstractGenerator other, Scheduler ... schedulers) {
        this.minDuration = other.minDuration;
        this.difference = other.difference;
        this.intensity = other.intensity;
        this.threshold = other.threshold;
        this.numOfProcesses = other.numOfProcesses;
        if (schedulers == null){
            this.schedulers = other.schedulers;
        } else {
            this.schedulers = schedulers;
        }
        this.access = 1;
        this.chance = other.chance;
    }

    protected boolean shouldGenerateProcess(){

        for (Scheduler scheduler : schedulers) if (scheduler.isDone()) return true;
        return false;
    }


}
