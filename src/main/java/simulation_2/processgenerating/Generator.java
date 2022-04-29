package simulation_2.processgenerating;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;

public class Generator implements IGenerator{

    private final RandomGenerator ACCESS_GENERATOR = new Well19937c(1);
    private final RandomGenerator POSITION_GENERATOR = new Well19937c(2);
    private final RandomGenerator MEAN_CHANGE_GENERATOR = new Well19937c(3);
    private final RandomGenerator MEAN_POSITION_GENERATOR = new Well19937c(4);
    private final RandomGenerator PRIORITY_DEADLINE_GENERATOR = new Well19937c(5);
    private final RandomGenerator PRIORITY_GENERATOR = new Well19937c(6);
    private final RandomGenerator GAUSS_POSITION_GENERATOR = new Well19937c(7);

    private Disc disc;

    private boolean generatePriorityRequests;
    // 1 - 100
    private double percentageOfPriorityRequests;
    private int deadline;
    // Value 0 - 1, chance * (totalPath - numOfCylinderMoves) will be used to compute
    // deadline. If 1 , then at the time the cylinder head will start to service this request,
    // it's deadline will be equal to the randomly generated value
    // it can make deadlines more feasible
    private double chanceForDeadline;

    private int discSize;
    private boolean gaussDist;
    // Numbers between 1 - 100, each is the percentage
    // of the total disc size and points to the area
    private int[] gaussDistMean;
    private final double sigma = Math.sqrt(2.0);
    private int currentMean;

    private int generatedRequests;
    private int generatedPriorityRequests;
    private final int totalRequests;

    private long sumOfPaths;

    private final double gaussMeanSwitchThreshold = 0.2;
    private final double priorityRequestsThreshold = 0.2;
    private final double chance = 0.1;
    private double currentChance = 0.1;

    public Generator(Disc disc, int totalRequests, boolean generatePriorityRequests, double percentageOfPriorityRequests, int deadline, double excess, int discSize, boolean gaussDist, int[] gaussDistMean) {
        this.disc = disc;
        this.totalRequests = totalRequests;
        this.generatePriorityRequests = generatePriorityRequests;
        this.percentageOfPriorityRequests = percentageOfPriorityRequests;
        this.deadline = deadline;
        this.chanceForDeadline = excess;
        this.discSize = discSize;
        this.gaussDist = gaussDist;
        this.gaussDistMean = gaussDistMean;
    }

    public Generator(Generator other) {
        this.disc = other.disc;
        this.totalRequests = other.totalRequests;
        this.generatePriorityRequests = other.generatePriorityRequests;
        this.percentageOfPriorityRequests = other.percentageOfPriorityRequests;
        this.deadline = other.deadline;
        this.chanceForDeadline = other.chanceForDeadline;
        this.discSize = other.discSize;
        this.gaussDist = other.gaussDist;
        this.gaussDistMean = other.gaussDistMean;
    }

    private int getGaussRequestPosition(){

        changeCurrentMean();

        return calculateGaussianPosition();
    }

    private int calculateGaussianPosition(){
        return (int)Math.min(discSize, Math.abs((GAUSS_POSITION_GENERATOR.nextGaussian() / (double)gaussDistMean.length * sigma
                + gaussDistMean[currentMean] * .01) * discSize));
    }

    private void changeCurrentMean(){
        if (MEAN_CHANGE_GENERATOR.nextFloat() < gaussMeanSwitchThreshold){
            currentMean = MEAN_POSITION_GENERATOR.nextInt(gaussDistMean.length);
            System.out.println("[CURRENT MEAN] : " + currentMean);
        }
    }

    private int getDeadline(){
        // Decide whether this should be priority request
        if (generatePriorityRequests && (PRIORITY_GENERATOR.nextFloat() < priorityRequestsThreshold)
        && generatedPriorityRequests < (percentageOfPriorityRequests * totalRequests)){
            generatedPriorityRequests++;
            int value = PRIORITY_DEADLINE_GENERATOR.nextInt(deadline);
            return (int) ((sumOfPaths - disc.getNumOfHeadMoves()) * chanceForDeadline) + value;
        } else return 0;
    }

    private int getPosition(){
        if (gaussDist){
            return getGaussRequestPosition();
        } else return POSITION_GENERATOR.nextInt(discSize);
    }

    @Override
    public Request next() {

        if (generatedRequests < totalRequests && ((double) sumOfPaths / (disc.getNumOfHeadMoves() + 1) <= ACCESS_GENERATOR.nextFloat() + currentChance||
        generatedRequests - disc.getNumOfRealizedRequests() <= 1)){
            int position = getPosition();
            int deadline = getDeadline();
            Request request = new Request(position, disc.getNumOfHeadMoves(), deadline);
            generatedRequests++;
            sumOfPaths += position;
            currentChance = Math.max(0, currentChance - chance);
            return request;
        } else {
            currentChance += chance;
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return numOfGeneratedRequests() != totalRequests;
    }

    @Override
    public int numOfGeneratedRequests() {
        return generatedRequests;
    }
}
