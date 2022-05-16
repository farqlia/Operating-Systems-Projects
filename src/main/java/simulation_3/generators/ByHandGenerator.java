package simulation_3.generators;

import java.util.*;
import java.util.stream.Collectors;

public class ByHandGenerator implements Generator{

    // TRY THIS ONE WITH CACHE EQUAL TO 3 AND 4 AND COMPARE FCFS
    int pageRange = 5;
    int[] pages = {0, 1, 2, 3, 0, 1, 4, 0, 1, 2, 3, 4};
    int size;

    public ByHandGenerator(int size){
        this.size = size;
    }

    @Override
    public int getPageRange() {
        return pageRange;
    }

    @Override
    public ListIterator<Integer> iterator() {
        return new InnerIterator();
    }

    @Override
    public List<Integer> requests() {
        return Arrays.stream(pages).boxed().collect(Collectors.toList());
    }

    @Override
    public int size() {
        return pageRange;
    }

    private class InnerIterator implements ListIterator<Integer>{

        int counter = 0;

        @Override
        public boolean hasNext() {
            return counter < size;
        }

        @Override
        public Integer next() {
            if (hasNext()){
                return pages[counter++ % pages.length];
            } else throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Integer previous() {
            return pages[(counter - 1) % pages.length];
        }

        @Override
        public int nextIndex() {
            return counter % pages.length;
        }

        @Override
        public int previousIndex() {
            return (counter - 1) % pages.length;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(Integer integer) {

        }

        @Override
        public void add(Integer integer) {

        }
    }

}
