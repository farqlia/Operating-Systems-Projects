package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class ProcessesGenerator extends AbstractGenerator{


    // Max difference should be num between 1 and 9
    // threshold - what is the threshold that the process/processes will be generated
    public ProcessesGenerator(int minDuration, int difference, int intensity,
                              int numOfProcesses, Scheduler ... schedulers) {
        super(minDuration, difference, intensity, numOfProcesses,schedulers);
    }

    // copy constructor
    public ProcessesGenerator(AbstractGenerator other, Scheduler ... schedulers) {
        super(other, schedulers);
    }

    @Override
    protected int getCompletionTime() {
        return minDuration + (int) Math.abs((difference * NUM_GENERATOR.nextGaussian()));
    }

}
