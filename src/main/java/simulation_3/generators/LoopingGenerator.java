package simulation_3.generators;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoopingGenerator implements Generator{

    private final List<Integer> pages =
            IntStream.range(0, 20).boxed().collect(Collectors.toList());

    private int size;

    public LoopingGenerator(int size){
        this.size = size;
    }

    @Override
    public ListIterator<Integer> iterator() {
        return new InnerIterator();
    }

    @Override
    public List<Integer> requests() {
        return pages;
    }

    @Override
    public int getPageRange() {
        return 20;
    }

    private class InnerIterator implements ListIterator<Integer> {

        private int i;

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Integer next() {
            if (hasNext()){
                return pages.get(i++ % pages.size());
            } else throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return i % pages.size();
        }

        @Override
        public boolean hasPrevious() {return false;}

        @Override
        public Integer previous() {return null;}

        @Override
        public int previousIndex() {return 0;}

        @Override
        public void remove() {}

        @Override
        public void set(Integer integer) {}

        @Override
        public void add(Integer integer) {}
    }
}
