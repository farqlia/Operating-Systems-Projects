package simulation_5;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

import java.util.Iterator;

public class DurationGenerator implements Iterable<Integer>{

    RandomGenerator rG = new MersenneTwister(666);

    int lower;
    int upper;

    public DurationGenerator(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new InnerIterator();
    }

    private class InnerIterator implements Iterator<Integer>{

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return rG.nextInt(upper - lower + 1) + lower;
        }
    }

}
