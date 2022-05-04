package simulation_2.processgenerating;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class DeadlineProducer implements Producer{

    private final RandomGenerator MEAN_CHANGE_GENERATOR = new Well19937c(8);
    private final RandomGenerator MEAN_POSITION_GENERATOR = new Well19937c(9);
    private final RandomGenerator PRIORITY_DEADLINE_GENERATOR = new Well19937c(5);
    private final RandomGenerator PRIORITY_GENERATOR = new Well19937c(6);

    private final double priorityRequestsThreshold = 0.4;
    private final int totalRequests;
    private boolean generatePR;
    private int numOfPR;
    private int numOfGeneratedPR;
    private int currentMean;
    private double gaussDistMeans[];

    public DeadlineProducer(int totalRequests, double[] gaussDistMeans,
                            boolean generatePR, int numOfPR) {
        this.generatePR = generatePR;
        this.numOfPR = numOfPR;
        this.totalRequests = totalRequests;
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
            return calculateGaussianCoefficient();
        } else return 0;
    }

    @Override
    public double produce() {
        return getDeadline();
    }

    @Override
    public int numOfProduced() {
        return numOfGeneratedPR;
    }
}
