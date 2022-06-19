package simulation_5;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497a;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParametrizedDemandGenerator implements Iterable<Integer> {

    private final Demand[] demands;
    private final RandomGenerator phaseDurationGenerator = new Well44497a(1);
    private final RandomGenerator nextDemandGenerator = new Well44497a(2);
    private final RandomGenerator demandValueGenerator = new Well44497a(3);

    private final int numOfProcesses;

    private final double phaseDurationThreshold = 0.5;

    public ParametrizedDemandGenerator(Demand[] demands, int numOfProcesses){
        this.demands = demands;
        this.numOfProcesses = numOfProcesses;
    }

    public int demandIndex(int current){
        if (phaseDurationGenerator.nextFloat() < phaseDurationThreshold || demands[current].percent == 0){
            do {
                current = nextDemandGenerator.nextInt(demands.length);
            } while (demands[current].percent == 0);
        }
        return current;
    }

    private class InnerIterator implements Iterator<Integer>{
        int current;
        int howMany;
        int nulledDemands;
        @Override
        public boolean hasNext () {
            if (howMany == 0){

                if (demands[current].percent == 0) nulledDemands++;

                if (nulledDemands != demands.length) {
                        current = demandIndex(current);
                        howMany = (int)(numOfProcesses * 0.01) ;
                        demands[current].percent--;
                    }
                }

            return nulledDemands != demands.length;
        }

        @Override
        public Integer next() {
            if (hasNext()){
                howMany--;
                return demandValueGenerator.nextInt(demands[current].upper - demands[current].lower + 1)
                        + demands[current].lower;
            } else {throw new NoSuchElementException();}
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new InnerIterator();
    }
}
