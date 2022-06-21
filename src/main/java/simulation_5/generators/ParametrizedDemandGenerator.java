package simulation_5.generators;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well44497a;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ParametrizedDemandGenerator implements Iterable<Integer> {

    private final Demand[] demands;
    private final RandomDataGenerator phaseDurationGenerator = new RandomDataGenerator(new Well44497a(1));
    private final RandomGenerator nextDemandGenerator = new Well44497a(2);
    private final RandomGenerator demandValueGenerator = new Well44497a(3);

    private int[] demandsNumber;
    private final int numOfProcesses;
    private double maxProbability;

    public ParametrizedDemandGenerator(Demand[] demands, int numOfProcesses){
        this.demands = Arrays.copyOf(demands, demands.length);
        Arrays.stream(this.demands).sorted(Comparator.comparingInt(d -> d.percent));
        this.maxProbability = Arrays.stream(this.demands).max(Comparator.comparingDouble(d -> d.probability)).get().probability;
        this.numOfProcesses = numOfProcesses;
        this.demandsNumber = new int[demands.length];
        computeDemands(demandsNumber);
    }

    private void computeDemands(int[] demandsNumber){
        for (int i = 0; i < demands.length; i++){
            demandsNumber[i] = demands[i].percent * numOfProcesses;
        }
    }

    public int demandIndex(){

        int current;
        double probability = phaseDurationGenerator.nextUniform(0, maxProbability);
        do {
            current = nextDemandGenerator.nextInt(demands.length);
        } while (demands[current].probability < probability
                && demandsNumber[current] != 0);

        while (demandsNumber[current] == 0)
            current = nextDemandGenerator.nextInt(demands.length);

        return current;
    }

    private class InnerIterator implements Iterator<Integer>{

        int current;
        int nulledDemands;

        @Override
        public boolean hasNext () {

            if (demandsNumber[current] == 0) nulledDemands++;

            if (nulledDemands != demands.length) {
                current = demandIndex();
            }

            return nulledDemands != demands.length;
        }

        @Override
        public Integer next() {
            if (hasNext()){
                demandsNumber[current]--;
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
