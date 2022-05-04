package simulation_2.processgenerating;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;

public class BaseGenerator implements Generator {

    private final RandomGenerator ACCESS_GENERATOR = new Well19937c(1);

    private final Disc disc;
    private final Producer positionProducer;

    private final int totalRequests;

    private final int threshold = 10;

    private double currentChance = -0.005;
    private int generatedRequests;

    private final RandomGenerator MEAN_CHANGE_GENERATOR = new Well19937c(8);
    private final RandomGenerator MEAN_POSITION_GENERATOR = new Well19937c(9);
    private final RandomGenerator PRIORITY_DEADLINE_GENERATOR = new Well19937c(5);
    private final RandomGenerator PRIORITY_GENERATOR = new Well19937c(6);

    private final double priorityRequestsThreshold = 0.4;
    private boolean generatePR;
    private int numOfPR;
    private int numOfGeneratedPR;
    private int currentMean;
    private double gaussDistMeans[];

    public BaseGenerator(Disc disc, int totalRequests, double[] gaussDistMeans,
                         boolean generatePR, int numOfPR, Producer positionProducer) {
        this.disc = disc;
        this.totalRequests = totalRequests;
        this.positionProducer = positionProducer;
        this.generatePR = generatePR;
        this.numOfPR = numOfPR;
        this.gaussDistMeans = gaussDistMeans;
    }

    private void changeCurrentMean(){
        if (MEAN_CHANGE_GENERATOR.nextFloat() < priorityRequestsThreshold){
            currentMean = MEAN_POSITION_GENERATOR.nextInt(gaussDistMeans.length);
            //System.out.println("[DP][CURRENT MEAN] : " + currentMean);
        }
    }

    // This method generates the coefficient that is used by the base generator
    private double calculateGaussianCoefficient(){
        // gaussian_normal * sqrt(sigma)
        return Math.abs((PRIORITY_DEADLINE_GENERATOR.nextGaussian()) * Math.sqrt(gaussDistMeans[currentMean]));
        //return Math.abs((PRIORITY_DEADLINE_GENERATOR.nextGaussian() * sigma
        //       + gaussDistMeans[currentMean] * .01));
    }

    private boolean shouldGeneratePriorityRequest(){
        return generatePR && (PRIORITY_GENERATOR.nextFloat() < priorityRequestsThreshold)
                && (numOfGeneratedPR < numOfPR);
    }

    private double getDeadline(){
        // Decide whether this should be priority request
        if (shouldGeneratePriorityRequest()){
            changeCurrentMean();
            numOfGeneratedPR++;

        } else return 0;
        return 0;
    }

    private Request produceRequest(){
        int position = (int)positionProducer.produce();
        if (shouldGeneratePriorityRequest()){
            int deadline = (int) (Math.abs((PRIORITY_DEADLINE_GENERATOR.nextGaussian()) *
                    Math.sqrt(gaussDistMeans[currentMean]))
                    * Math.abs(position - disc.getHeadPosition()));
        }
        int deadline = 0;
        return new Request(position, disc.getNumOfHeadMoves(), deadline);
    }

    @Override
    public Request next() {
        currentChance *= (-1);
        if (generatedRequests < totalRequests && ((generatedRequests - disc.getNumOfRequests())
                / (double) threshold <= ACCESS_GENERATOR.nextFloat() + currentChance||
                generatedRequests - disc.getNumOfRequests() <= 1)){
            return produceRequest();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        return generatedRequests != totalRequests;
    }

    @Override
    public int getNumberOfGenerated() {
        return generatedRequests;
    }
}
