package lab1.processesgenerator;

import lab1.processing.SimpleProcess;
import lab1.schedulers.Scheduler;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class Generator {

    int minDuration;
    // max difference between the length of processes
    int difference;
    double access;
    // Let the chance raise for the next process
    double chance = 0.1;
    // How many processes can enter at the same time
    int intensity;
    int numOfProcesses;

    // Value between 0 - 1 : tells when longer processes have to enter
    // If it is 0, then this will be a normal generator
    private final double phase;
    // How many longer processes
    private final double phaseLength;
    private final int multiplicant;

    private final int NUM_OF_PROCESSES;

    private final RandomGenerator ACCESS_GENERATOR = new Well19937c(1);
    private final RandomGenerator NUM_GENERATOR = new Well19937c(2);
    private final RandomGenerator INTENSITY_GENERATOR = new Well19937c(3);

    private final ChiSquaredDistribution chiSqrtDis;

    private final Scheduler[] schedulers;

    int counter;
    // Time completion of all the current processes
    int timeToComplete;

    // phase is number between 0 - 8
    public Generator(int minDuration, int difference, int intensity,
                     int numOfProcesses, double phase, double phaseLength, int multiplicant, Scheduler... schedulers) {
        this.minDuration = minDuration;
        this.difference = difference;
        this.intensity = intensity;
        this.numOfProcesses = numOfProcesses;
        this.access = 10;
        this.phase = phase;
        this.phaseLength = phaseLength;
        this.multiplicant = multiplicant;
        this.schedulers = schedulers;
        this.NUM_OF_PROCESSES = numOfProcesses;
        this.chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
    }

    public Generator(Generator other, Scheduler ... schedulers) {
        this.minDuration = other.minDuration;
        this.difference = other.difference;
        this.intensity = other.intensity;
        this.numOfProcesses = other.numOfProcesses;
        this.phaseLength = other.phaseLength;
        this.phase = other.phase;
        this.multiplicant = other.multiplicant;
        this.NUM_OF_PROCESSES = other.numOfProcesses;
        if (schedulers == null){
            this.schedulers = other.schedulers;
        } else {
            this.schedulers = schedulers;
        }
        this.access = 1;
        this.chance = other.chance;
        this.chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
    }

    public void next() {

        if (ACCESS_GENERATOR.nextFloat() + access > (double)((timeToComplete) / (Time.get() + 1))
                || (Time.get() - timeToComplete == 1)){

            int howMany = 1 + (int)((intensity - 1) * chiSqrtDis.cumulativeProbability(9 * INTENSITY_GENERATOR.nextFloat()));

            howMany = Math.min(howMany, numOfProcesses);

            while (howMany-- > 0){
                int completionTime = getCompletionTime();

                SimpleProcess p = new SimpleProcess(counter++, Time.get(), completionTime);

                for (Scheduler scheduler : schedulers){
                    scheduler.addProcess(new SimpleProcess(p));
                }
                timeToComplete += completionTime;
                numOfProcesses--;
                if (numOfProcesses == 0) System.out.println("[LAST PROCESS ADDED][TIME] : " + Time.get() + "| " + p);
            }
            access = howMany * (-chance);
        } else {
            access += chance;
        }

    }

    private int getCompletionTime() {
        int completionTime = getPhase();
        if (completionTime == 0) completionTime = minDuration + (int) Math.abs((difference * NUM_GENERATOR.nextGaussian()));
        return completionTime;
    }

    private int getPhase(){
        int val = (int) (phase * NUM_OF_PROCESSES);
        if (phase != 0 && totalGenerated() >= val && totalGenerated() <= val + (phaseLength * NUM_OF_PROCESSES)){
            return minDuration + (int) Math.abs(multiplicant * difference * NUM_GENERATOR.nextGaussian());
        }
        return 0;
    }

    public int totalGenerated() {
        return counter;
    }

}
