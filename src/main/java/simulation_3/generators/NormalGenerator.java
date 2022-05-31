package simulation_3.generators;

import org.apache.commons.math3.random.*;
import simulation_3.PrintStatistics;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NormalGenerator implements Generator{

    private final RandomDataGenerator PAGE_GENERATOR;
    private final RandomDataGenerator SET_SIZE_GENERATOR;
    private final RandomGenerator SET_CHOICE;
    private final RandomDataGenerator LOCALITY_INTERVAL_GENERATOR;

    private final RandomGenerator RANDOMNESS_GENERATOR;
    private final RandomGenerator PAGE_CHOICE_GENERATOR;
    private int numberOfSets;

    // 2-3 element sets
    private List<List<Integer>> sets = new ArrayList<>();

    private final int pageNumbers;

    private List<Integer> pages;

    private int size;
    private final List<Integer> pageRequests = new ArrayList<>(size);

    public NormalGenerator(int size, int pageNumbers){
        this(1, size, pageNumbers, 5);
    }

    public NormalGenerator(int randInt, int size, int pageNumbers, int numberOfSets){
        this.size = size;
        this.pageNumbers = pageNumbers;
        this.numberOfSets = numberOfSets;
        PAGE_GENERATOR = new RandomDataGenerator(new Well19937c(randInt));
        SET_SIZE_GENERATOR = new RandomDataGenerator(new Well512a(randInt));
        SET_CHOICE = new Well1024a(randInt);
        LOCALITY_INTERVAL_GENERATOR = new RandomDataGenerator(new Well19937a(randInt));
        RANDOMNESS_GENERATOR = new Well44497b(randInt);
        PAGE_CHOICE_GENERATOR = new Well44497a(randInt);
        init();
    }

    public void init(){
        this.pages = IntStream.range(0, pageNumbers)
                .boxed().collect(Collectors.toList());
        fillLocalSets();
        createRequests();
    }

    private void createRequests(){
        while (pageRequests.size() < size){
            // How often randomness occur between intervals of local requests
            double randomInterrupt = 0.2;
            if (randomInterrupt > RANDOMNESS_GENERATOR.nextFloat()){
                addPageRandomly();
            } else {
                addPagesLocally(sets.get(SET_CHOICE.nextInt(sets.size())));
            }
        }
    }

    private void addPagesLocally(List<Integer> set){
        // Min and max number of references
        int lowDeltaT = 20;
        int highDeltaT = 40;
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

    private void addPageRandomly(){
        int rndPage = pages.get(RANDOMNESS_GENERATOR.nextInt(pages.size()));
        if (PrintStatistics.print) System.out.println("[RANDOM PAGE]: " + rndPage);
        pageRequests.add(rndPage);
    }

    private void fillLocalSets(){
        List<Integer> set = null;
        // Index of page
        int page_i;
        int setSize;
        int bound = 2;
        int delta = 2;
        while (numberOfSets-- > 0 && pages.size() > bound){
            page_i = PAGE_GENERATOR.nextInt(0, pages.size());
            setSize = Math.min(pages.size() - bound, SET_SIZE_GENERATOR.nextInt(2, 5));
            set = new ArrayList<>(setSize);

            while (setSize-- > 0 && pages.size() > (bound + setSize)){
                // We want to choose pages from its neighbours
                // max difference between pages in one set

                addFromRange(page_i - delta, page_i + delta, set);
            }
            if (!set.isEmpty()) sets.add(set);
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
