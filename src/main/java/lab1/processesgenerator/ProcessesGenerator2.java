package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class ProcessesGenerator2 extends Generator{

    private int minDuration;
    // max difference between the length of processes
    private int difference;
    private double threshold;
    private double access;
    // Let the chance raise for the next process
    private double chance;
    // How many processes can enter at the same time
    private int intensity;
    private int numOfProcesses;
    private RandomGenerator ACCESS_GENERATOR = new Well19937c(1);
    private RandomGenerator NUM_GENERATOR = new Well19937c(2);
    private RandomGenerator INTENSITY_GENERATOR = new Well19937c(3);
    ChiSquaredDistribution chiSqrtDis;
    private LogNormalDistribution logNDis = new LogNormalDistribution();

    private Scheduler[] schedulers;

    int counter;

    // Max difference should be num between 1 and 9
    // threshold - what is the threshold that the process/processes will be generated
    public ProcessesGenerator2(int minDuration, int difference, int intensity, double threshold,
                               int numOfProcesses, Scheduler ... schedulers) {
        this.minDuration = minDuration;
        this.difference = difference;
        this.intensity = intensity;
        this.schedulers = schedulers;
        this.threshold = threshold;
        this.numOfProcesses = numOfProcesses;
        chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
        this.access = 1;
        this.chance = 0.1;
    }

    // copy constructor
    public ProcessesGenerator2(ProcessesGenerator2 other, Scheduler ... schedulers) {
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
        this.chance = 0.1;
        chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - other.intensity));
    }

    @Override
    public Process_ next(){

        SimpleProcess p = null;
        //(ACCESS_GENERATOR.nextFloat() + access
        if (ACCESS_GENERATOR.nextFloat() + access > threshold){

            int howMany = 1 + (int)((intensity - 1) * chiSqrtDis.cumulativeProbability(9 * INTENSITY_GENERATOR.nextFloat()));

            howMany = Math.min(howMany, numOfProcesses);

            while (howMany-- > 0){
                int completionTime = minDuration + (int) Math.abs((difference * NUM_GENERATOR.nextGaussian()));

                p = new SimpleProcess(counter++, Time.get(), completionTime);
                for (Scheduler scheduler : schedulers){
                    scheduler.addProcess(new SimpleProcess(p));
                }
                numOfProcesses--;

            }
            access = chance;
        } else {
            access += chance;
        }
        return p;
    }

    @Override
    public int totalGenerated() {
        return counter;
    }

}
