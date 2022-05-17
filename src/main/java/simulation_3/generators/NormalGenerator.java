package simulation_3.generators;

import org.apache.commons.math3.random.*;
import simulation_3.PrintStatistics;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NormalGenerator implements Generator{

    private final RandomDataGenerator PAGE_GENERATOR = new RandomDataGenerator(new Well19937c(1));
    private final RandomDataGenerator SET_SIZE_GENERATOR = new RandomDataGenerator(new Well512a(1));
    private final RandomGenerator SET_CHOICE = new Well1024a(1);
    private final RandomDataGenerator LOCALITY_INTERVAL_GENERATOR = new RandomDataGenerator(new Well512a(2));

    private final RandomGenerator RANDOMNESS_GENERATOR = new Well512a(5);
    private final RandomGenerator PAGE_CHOICE_GENERATOR = new Well1024a(6);


    // 2-3 element sets
    private List<List<Integer>> sets = new ArrayList<>();
    private int numberOfSets = 5;
    // Min and max number of references
    private int lowDeltaT = 10;
    private int highDeltaT = 20;

    // max difference between pages in one set
    private int delta = 2;
    private int pageNumbers = 20;

    private List<Integer> pages = IntStream.range(0, pageNumbers)
            .boxed().collect(Collectors.toList());

    private int size;

    // How often randomness occur between intervals of local requests
    private double randomInterrupt = 0.2;

    private List<Integer> pageRequests = new ArrayList<>(size);

    public NormalGenerator(int size){
        this.size = size;
        init();
    }

    public void init(){
        fillLocalSets();
        createRequests();
    }

    private void createRequests(){
        while (pageRequests.size() < size){
            if (randomInterrupt > RANDOMNESS_GENERATOR.nextFloat()){
                addRandom();
            } else {
                addLocally(sets.get(SET_CHOICE.nextInt(sets.size())));
            }
        }
    }

    private void addLocally(List<Integer> set){
        int intervalSize = LOCALITY_INTERVAL_GENERATOR.nextInt(lowDeltaT, highDeltaT);
        if (PrintStatistics.print) System.out.println("[LOCAL INTERVAL]: ");
        int page = set.get(PAGE_CHOICE_GENERATOR.nextInt(set.size()));
        for (int i = 0; (i < intervalSize) && (pageRequests.size() < size); i++){
            if (PrintStatistics.print) System.out.print(page + " ");
            pageRequests.add(page);
            page = set.get(PAGE_CHOICE_GENERATOR.nextInt(set.size()));
        }
        if (PrintStatistics.print) System.out.println();
    }

    private void addRandom(){
        int rndPage = pages.get(RANDOMNESS_GENERATOR.nextInt(pages.size()));
        if (PrintStatistics.print) System.out.println("[RANDOM PAGE]: " + rndPage);
        pageRequests.add(rndPage);
    }

    private void fillLocalSets(){
        List<Integer> set = new ArrayList<>();
        // Index of page
        int page_i;
        int setSize;
        while (numberOfSets-- > 0){
            page_i = PAGE_GENERATOR.nextInt(0, pages.size());
            setSize = SET_SIZE_GENERATOR.nextInt(2, 5);
            while (setSize-- > 0){
                // We want to choose pages from its neighbours
                addFromRange(page_i - delta, page_i + delta, set);
            }
            sets.add(new ArrayList<>(set));
            set.clear();
        }
    }

    private void addFromRange(int low, int high, List<Integer> set){
        low = Math.max(0, low);
        high = Math.min(high, pages.size() - 1);

        if (low > high) {
            addFromRange(high, low, set);
            return;
        }

        set.add(pages.remove(PAGE_GENERATOR.nextInt(low, high)));
    }

    @Override
    public ListIterator<Integer> iterator() {
        return new InnerIterator();
    }

    @Override
    public List<Integer> requests() {
        return pageRequests;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int getPageRange() {
        return pageNumbers;
    }

    private class InnerIterator implements ListIterator<Integer> {

        int counter = 0;

        @Override
        public boolean hasNext() {
            return counter < pageRequests.size();
        }

        @Override
        public Integer next() {
            if (hasNext()) {return pageRequests.get(counter++);}
            else throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return counter;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Integer previous() {
            return pageRequests.get(previousIndex());
        }

        @Override
        public int previousIndex() {
            return counter - 1;
        }

        @Override
        public void remove() {}

        @Override
        public void set(Integer integer) {

        }

        @Override
        public void add(Integer integer) {

        }
    }

}
