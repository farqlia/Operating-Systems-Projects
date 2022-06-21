package simulation_5.generators;

import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.Iterator;

public class GaussProcessorDist implements Iterable<Integer>{

    int mean;
    double sigma;
    int upper;
    int numOfProcessors;

    public GaussProcessorDist(int numOfProcessors){
        this.numOfProcessors = numOfProcessors;
    }

    private class InnerIterator implements Iterator<Integer>{

        RandomDataGenerator gRG
                = new RandomDataGenerator(new MersenneTwister(1));
        int multiplier;

        public InnerIterator(){
            this.multiplier = numOfProcessors / 2;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return Math.min(numOfProcessors - 1,
                    Math.abs((int) (gRG.nextGaussian(0, 1) * multiplier)));
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new InnerIterator();
    }
}
