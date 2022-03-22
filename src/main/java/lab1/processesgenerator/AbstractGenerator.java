package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public abstract class AbstractGenerator extends Generator{

    int minDuration;
    // max difference between the length of processes
    int difference;
    double access;
    // Let the chance raise for the next process
    double chance = 0.1;
    // How many processes can enter at the same time
    int intensity;
    int numOfProcesses;

    RandomGenerator ACCESS_GENERATOR = new Well19937c(1);
    RandomGenerator NUM_GENERATOR = new Well19937c(2);
    RandomGenerator INTENSITY_GENERATOR = new Well19937c(3);

    ChiSquaredDistribution chiSqrtDis;

    Scheduler[] schedulers;

    int counter;

    int timeToComplete;

    // phase is number between 0 - 8
    public AbstractGenerator(int minDuration, int difference, int intensity,
                            int numOfProcesses, Scheduler... schedulers) {
        this.minDuration = minDuration;
        this.difference = difference;
        this.intensity = intensity;
        this.numOfProcesses = numOfProcesses;
        this.access = 1;
        this.schedulers = schedulers;
        this.chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
    }

    public AbstractGenerator(AbstractGenerator other, Scheduler ... schedulers) {
        this.minDuration = other.minDuration;
        this.difference = other.difference;
        this.intensity = other.intensity;
        this.numOfProcesses = other.numOfProcesses;
        if (schedulers == null){
            this.schedulers = other.schedulers;
        } else {
            this.schedulers = schedulers;
        }
        this.access = 1;
        this.chance = other.chance;
        this.chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
    }

    protected abstract int getCompletionTime();

    @Override
    public Process_ next() {

        SimpleProcess p = null;
        if (ACCESS_GENERATOR.nextFloat() + access > (double)(timeToComplete /(Time.get() + 1)) || (Time.get() - timeToComplete == 0)){

            int howMany = 1 + (int)((intensity - 1) * chiSqrtDis.cumulativeProbability(9 * INTENSITY_GENERATOR.nextFloat()));

            howMany = Math.min(howMany, numOfProcesses);

            while (howMany-- > 0){
                int completionTime = getCompletionTime();

                p = new SimpleProcess(counter++, Time.get(), completionTime);

                for (Scheduler scheduler : schedulers){
                    scheduler.addProcess(new SimpleProcess(p));
                }
                timeToComplete += completionTime;
                numOfProcesses--;
                if (numOfProcesses == 0) System.out.println("[LAST PROCESS ADDED][TIME] : " + Time.get() + "| " + p);
            }
            access = -chance;
        } else {
            access += Math.abs(chance);
        }
        return p;

    }

    @Override
    public int totalGenerated() {
        return counter;
    }

}
