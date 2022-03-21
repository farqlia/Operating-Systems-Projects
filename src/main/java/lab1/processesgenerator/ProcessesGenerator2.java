package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class ProcessesGenerator2 extends AbstractGenerator{

    ChiSquaredDistribution chiSqrtDis;

    // Max difference should be num between 1 and 9
    // threshold - what is the threshold that the process/processes will be generated
    public ProcessesGenerator2(int minDuration, int difference, int intensity, double threshold, double chance,
                               int numOfProcesses, Scheduler ... schedulers) {
        super(minDuration, difference, intensity, threshold, numOfProcesses, chance, schedulers);
        chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
    }

    // copy constructor
    public ProcessesGenerator2(AbstractGenerator other, Scheduler ... schedulers) {
        super(other, schedulers);
        chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - other.intensity));
    }

    @Override
    public Process_ next(){

        SimpleProcess p = null;
        //(ACCESS_GENERATOR.nextFloat() + access
        if ((ACCESS_GENERATOR.nextFloat() + access > threshold) || shouldGenerateProcess()){

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
            access += Math.abs(chance);
        }
        return p;
    }

    @Override
    public int totalGenerated() {
        return counter;
    }

}
