package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class SJFKillGenerator extends AbstractGenerator{

    // Value between 0 - 1 : tells when the big processes have to enter
    // Can be zero, then this will be a normal generator
    private double phase;
    // How many big processes
    private double phaseLength;
    private int multiplicant;
    private final int NUM_OF_PROCESSES;

    ChiSquaredDistribution chiSqrtDis;

    public SJFKillGenerator(int minDuration, int difference, int intensity, double threshold,
                               int numOfProcesses, double chance, double phase, double phaseLength, int multiplicant, Scheduler... schedulers) {
         super(minDuration, difference, intensity, threshold, numOfProcesses, chance, schedulers);
         this.phaseLength = phaseLength;
         this.phase = phase;
         this.NUM_OF_PROCESSES = numOfProcesses;
         this.multiplicant = multiplicant;
         chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
    }

    public SJFKillGenerator(SJFKillGenerator other, Scheduler ... schedulers) {
        super(other, schedulers);
        this.phase = other.phase;
        this.NUM_OF_PROCESSES = other.NUM_OF_PROCESSES;
        this.phaseLength = other.phaseLength;
        this.multiplicant = other.multiplicant;
        chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - other.intensity));
    }

    @Override
    public Process_ next() {



        SimpleProcess p = null;
        if ((ACCESS_GENERATOR.nextFloat() + access > threshold) || shouldGenerateProcess()){

            int howMany = 1 + (int)((intensity - 1) * chiSqrtDis.cumulativeProbability(9 * INTENSITY_GENERATOR.nextFloat()));

            howMany = Math.min(howMany, numOfProcesses);

            while (howMany-- > 0){
                int completionTime = getPhase();
                if (completionTime == 0) completionTime = minDuration + (int) Math.abs((difference * NUM_GENERATOR.nextGaussian()));

                p = new SimpleProcess(counter++, Time.get(), completionTime);
                for (Scheduler scheduler : schedulers){
                    scheduler.addProcess(new SimpleProcess(p));
                }

                numOfProcesses--;
                if (numOfProcesses == 0) System.out.println("[LAST PROCESS ADDED][TIME] : " + Time.get() + "| " + p);
            }
            access = chance;
        } else {
            access += Math.abs(chance);
        }
        return p;

    }

    private int getPhase(){

        int val = (int) (phase * NUM_OF_PROCESSES);
        if (phase != 0 && totalGenerated() >= val && totalGenerated() <= val + (phaseLength * NUM_OF_PROCESSES)){
            return minDuration + (int) Math.abs(multiplicant * difference * NUM_GENERATOR.nextGaussian());
        }
        return 0;
    }


    @Override
    public int totalGenerated() {
        return counter;
    }
}
