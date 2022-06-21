package simulation_5.generators;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497a;

import java.util.Iterator;

public class GaussianDurationGenerator implements Iterable<Integer> {

    private final double phaseDurationThreshold = 0.3;

    double sigma;
    int lowerBound;
    int upperBound;

    public GaussianDurationGenerator(double sigma, int lowerBound,
                                     int upperBound){
        this.sigma = sigma;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new InnerIterator();
    }

    private class InnerIterator implements Iterator<Integer>{

        private final RandomGenerator rG = new MersenneTwister(666);
        private final RandomDataGenerator gaussianMeanGenerator = new RandomDataGenerator(new MersenneTwister(69));
        private final RandomGenerator phaseDurationGenerator = new Well44497a(1);

        int currentMean;
        int counter;
        int changeMeanFreq = 10;

        public InnerIterator(){
            this.counter = 0;
            this.currentMean = (upperBound + lowerBound) / 2;
        }

        private void changeMean(){
            if (phaseDurationGenerator.nextFloat() < phaseDurationThreshold){
                currentMean = gaussianMeanGenerator.nextInt(lowerBound, upperBound);
                counter = 0;
            }
        }

        @Override
        public boolean hasNext () {
            return true;
        }

        @Override
        public Integer next () {
            if (counter++ > changeMeanFreq) changeMean();
            return (int) Math.min(upperBound,
                    Math.max(lowerBound, Math.abs(currentMean + (rG.nextGaussian() * sigma))));
        }
    }
}
