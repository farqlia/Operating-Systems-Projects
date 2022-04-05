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
    private int freqOfPriorityReq;
    private int deadline;
    // Value -1 - 1, excess * distance will be added to the deadline
    // It may simulate situations when the cylinder head is able/ not able
    // to get there before the deadline
    private double excess;

    private int discSize;
    private boolean gaussDist;
    // Numbers between 1 - 100, each is the percentage
    // of the total disc size and points to the
    // area
    private int[] gaussDistMean;
    private int currentMean;
    // Number from range 0 - 10 as the percentage
    // of the total number of requests
    // So how often to change this mean
    private int frequency;

    // Count of total generated requests
    private int generatedRequests;
    private int totalRequests;

    private long sumOfPaths;

    private final double threshold = 0.5;

    public Generator(Disc disc, boolean generatePriorityRequests, int freqOfPriorityReq, int deadline, double excess, int discSize, boolean gaussDist, int[] gaussDistMean, int frequency) {
        this.disc = disc;
        this.generatePriorityRequests = generatePriorityRequests;
        this.freqOfPriorityReq = freqOfPriorityReq;
        this.deadline = deadline;
        this.excess = excess;
        this.discSize = discSize;
        this.gaussDist = gaussDist;
        this.gaussDistMean = gaussDistMean;
        this.frequency = frequency;
    }

    public Generator(Generator other) {
        this.disc = other.disc;
        this.generatePriorityRequests = other.generatePriorityRequests;
        this.freqOfPriorityReq = other.freqOfPriorityReq;
        this.deadline = other.deadline;
        this.excess = other.excess;
        this.discSize = other.discSize;
        this.gaussDist = other.gaussDist;
        this.gaussDistMean = other.gaussDistMean;
        this.frequency = other.frequency;
    }

    private int getGaussRequestPosition(){

        if ((generatedRequests % frequency + MEAN_CHANGE_GENERATOR.nextFloat()) < threshold){
            currentMean = MEAN_POSITION_GENERATOR.nextInt(gaussDistMean.length);
        }

        return (int)Math.abs(GAUSS_POSITION_GENERATOR.nextGaussian() + gaussDistMean[currentMean] * .01 * discSize);

    }

    private int getDeadline(){
        // Decide whether this should be priority request
        if (generatePriorityRequests && (generatedRequests % freqOfPriorityReq < PRIORITY_GENERATOR.nextInt(freqOfPriorityReq / 2))){
            int value = PRIORITY_DEADLINE_GENERATOR.nextInt(deadline);
            return value + (int) (excess * value) + 1;
        } else return 0;
    }

    private int getPosition(){
        if (gaussDist){
            return getGaussRequestPosition();
        } else return POSITION_GENERATOR.nextInt(discSize);
    }

    @Override
    public Request next() {

        if (generatedRequests < totalRequests && ((double) sumOfPaths / disc.getNumOfCylinderHeadMoves() < ACCESS_GENERATOR.nextFloat() ||
        generatedRequests - disc.getNumOfRequests() == 1)){
            int position = getPosition();
            int deadline = getDeadline();
            Request request = new Request(position, disc.getNumOfCylinderHeadMoves(), deadline);
            generatedRequests++;
            sumOfPaths += position;
            return request;
        }
        return null;
    }
}
