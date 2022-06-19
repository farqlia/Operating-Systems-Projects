package simulation_5;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497a;

import java.util.Iterator;

public class GaussianDurationGenerator implements Iterable<Integer> {

    private final RandomDataGenerator rG = new RandomDataGenerator(new MersenneTwister(666));
    private final RandomGenerator phaseDurationGenerator = new Well44497a(1);

    double sigma;
    double mean;
    double lowerBound;
    double upperBound;


    public GaussianDurationGenerator(double sigma, double mean, double lowerBound,
                                     double upperBound){
        this.sigma = sigma;
        this.mean = mean;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new InnerIterator();
    }

    private class InnerIterator implements Iterator<Integer>{

        int currentMean;

        private void changeMean(){

        }

        @Override
        public boolean hasNext () {
            return true;
        }

        @Override
        public Integer next () {
            return (int) Math.min(upperBound,
                    Math.max(lowerBound, rG.nextGaussian(mean, sigma)));
        }
    }
}
