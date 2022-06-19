package simulation_5;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.Iterator;

public class GaussProcessorDist implements Iterable<Integer>{

    GaussianRandomGenerator gRG = new GaussianRandomGenerator(new MersenneTwister(1));
    int mean;
    double sigma;
    int upper;

    public GaussProcessorDist(double sigmaSquare, int mean, int upper){
        this.mean = mean;
        this.sigma = Math.sqrt(sigmaSquare);
        this.upper = upper;
    }

    private class InnerIterator implements Iterator<Integer>{

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return Math.min(upper, Math.max(0, (int) (gRG.nextNormalizedDouble()
                    * sigma + mean)));
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new InnerIterator();
    }
}
