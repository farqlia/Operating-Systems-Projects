package simulation_3;

import org.apache.commons.math3.random.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NormalGenerator {

    private final RandomDataGenerator PAGE_GENERATOR = new RandomDataGenerator(new Well19937c(1));
    private final RandomDataGenerator SET_SIZE_GENERATOR = new RandomDataGenerator(new Well512a(1));
    private final RandomGenerator SET_CHOOSE = new Well1024a(1);
    private final RandomDataGenerator LOCALITY_INTERVAL_GENERATOR = new RandomDataGenerator(new Well512a(2));

    private final RandomGenerator RANDOMNESS_GENERATOR = new Well512a(5);
    private final RandomGenerator PAGE_CHOOSE_GENERATOR = new Well512a(6);

    private final RandomGenerator NUMBER_OF_RANDOM = new Well512a(7);


    // 2-3 element sets
    private List<List<Integer>> sets;
    private int numberOfSets;
    // Min and max number of references
    private int lowDeltaT;
    private int highDeltaT;

    // max difference between pages in one set
    private int delta;
    private int pageNumbers;

    private int size;

    private double randomInterrupt = 0.5;

    private List<Integer> pages = IntStream.range(0, pageNumbers)
            .boxed().collect(Collectors.toList());

    private List<Integer> pageRequests = new ArrayList<>(size);

    private void createRequests(){
        for (int i = 0; i < size; i++){
            if (randomInterrupt < RANDOMNESS_GENERATOR.nextDouble()){
                addRandom();
            } else {
                addLocally(sets.get(SET_CHOOSE.nextInt(sets.size())));
            }
        }
    }

    private void addLocally(List<Integer> set){
        for (int i = 0; i < LOCALITY_INTERVAL_GENERATOR.nextInt(lowDeltaT, highDeltaT); i++){
            pageRequests.add(set.get(PAGE_CHOOSE_GENERATOR.nextInt(set.size())));
        }
    }

    private void addRandom(){
        pageRequests.add(pages.get(RANDOMNESS_GENERATOR.nextInt(pages.size())));
    }

    private void fillLocalSets(){
        List<Integer> set = new ArrayList<>();
        // Index of page
        int page_i;
        int i;
        while (numberOfSets-- > 0){
            page_i = PAGE_GENERATOR.nextInt(0, pages.size());
            i = SET_SIZE_GENERATOR.nextInt(2, 5);
            while (i-- > 0){
                // We want to choose pages from its neighbours
                set.add(pages.remove(PAGE_GENERATOR.nextInt(Math.max(0, page_i - delta),
                        Math.min(page_i + delta, pages.size() - 1))));
            }
        }
    }

}
