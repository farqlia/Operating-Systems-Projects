package simulation_2.processgenerating;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class PositionProducer implements Producer{

    private final RandomGenerator MEAN_CHANGE_GENERATOR = new Well19937c(3);
    private final RandomGenerator MEAN_POSITION_GENERATOR = new Well19937c(4);
    private final RandomGenerator GAUSS_POSITION_GENERATOR = new Well19937c(7);
    private final double sigma = Math.sqrt(0.2);
    private final double gaussMeanSwitchThreshold = 0.2;

    private final int discSize;

    // Numbers between 1 - 100, each is the percentage
    // of the total disc size and points to the area
    private int[] gaussDistMeans;
    private int currentMean;

    public PositionProducer(int discSize, int[] gaussDistMeans) {
        this.discSize = discSize;
        this.gaussDistMeans = gaussDistMeans;
    }

    private void changeCurrentMean(){
        if (MEAN_CHANGE_GENERATOR.nextFloat() < gaussMeanSwitchThreshold){
            currentMean = MEAN_POSITION_GENERATOR.nextInt(gaussDistMeans.length);
            //System.out.println("[PP][CURRENT MEAN] : " + currentMean);
        }
    }

    private int calculateGaussianPosition(){
        return (int)Math.min(discSize, Math.abs((GAUSS_POSITION_GENERATOR.nextGaussian() / (double)gaussDistMeans.length * sigma
                + gaussDistMeans[currentMean] * .01) * 100));
    }

    private int getGaussRequestPosition(){
        changeCurrentMean();
        return calculateGaussianPosition();
    }

    @Override
    public double produce() {
        return getGaussRequestPosition();
    }

    @Override
    public int numOfProduced() {
        return -1;//not important
    }
}
