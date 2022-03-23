package simulation_1.processesgenerator;

import simulation_1.processing.SimpleProcess;
import simulation_1.schedulers.Scheduler;
import simulation_1.schedulers.Time;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class Generator {

    int minDuration;
    // max difference between the length of processes
    int difference;
    double access;
    // Manipulate the chance to add new process
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

    int numOfProcessesInPhase;
    int currentNumOfProcessesInPhase;
    int phaseMoment;

    // phase is number between 0 - 8
    public Generator(int minDuration, int difference, int intensity,
                     int numOfProcesses, double phase, double phaseLength, int multiplicant, Scheduler... schedulers) {
        this.minDuration = minDuration;
        this.difference = difference;
        this.intensity = intensity;
        this.numOfProcesses = numOfProcesses;
        // Set to some high value to add a process at the beginning
        this.access = 10;
        this.phase = phase;
        this.phaseLength = phaseLength;
        this.multiplicant = multiplicant;
        this.schedulers = schedulers;
        this.chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
        this.numOfProcessesInPhase = (int) (phaseLength * numOfProcesses);
        this.NUM_OF_PROCESSES = numOfProcesses;
        this.numOfProcesses = numOfProcesses - numOfProcessesInPhase;
        this.phaseMoment = (int) (phase * NUM_OF_PROCESSES);
        this.currentNumOfProcessesInPhase = numOfProcessesInPhase;
    }

    public Generator(Generator other, Scheduler ... schedulers) {
        this.minDuration = other.minDuration;
        this.difference = other.difference;
        this.intensity = other.intensity;
        this.numOfProcesses = other.numOfProcesses;
        this.phaseLength = other.phaseLength;
        this.phaseMoment = other.phaseMoment;
        this.phase = other.phase;
        this.multiplicant = other.multiplicant;
        this.NUM_OF_PROCESSES = other.numOfProcesses;
        if (schedulers == null){
            this.schedulers = other.schedulers;
        } else {
            this.schedulers = schedulers;
        }
        this.access = 10;
        this.chance = other.chance;
        this.chiSqrtDis = new ChiSquaredDistribution(Math.max(1, 10 - this.intensity));
        this.numOfProcessesInPhase = other.numOfProcessesInPhase;
        this.currentNumOfProcessesInPhase = other.numOfProcessesInPhase;
    }

    // Adds next process(ess) to the schedulers' queues
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
        if (isPhase()) return getPhase();
        else return minDuration + (int) Math.abs((difference * NUM_GENERATOR.nextGaussian()));
    }

    private int getPhase(){
        if (isPhase()){
            numOfProcesses += currentNumOfProcessesInPhase;
            if (currentNumOfProcessesInPhase != 0) System.out.println("[PHASE] : " + Time.get() + ", " + currentNumOfProcessesInPhase);
            currentNumOfProcessesInPhase = 0;
            return minDuration + (int) Math.abs(multiplicant * difference * NUM_GENERATOR.nextGaussian());
        }
        return 0;
    }

    private boolean isPhase(){
        // val defines whether we are entering a phase
        return  (phase != 0 && totalGenerated() >= phaseMoment && totalGenerated() <= numOfProcessesInPhase + phaseMoment);
    }

    public int totalGenerated() {
        return counter;
    }

}
