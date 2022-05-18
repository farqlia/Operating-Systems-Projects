package simulation_3.generators;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoopingGenerator implements Generator{

    private final List<Integer> pages;

    private int size;

    public LoopingGenerator(int size, int pageNumbers){
        this.size = size;
        this.pages =
                IntStream.range(0, pageNumbers).boxed().collect(Collectors.toList());
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
    public int size() {
        return size;
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
        public Integer previous() {return pages.get(previousIndex());}

        @Override
        public int previousIndex() {return (i - 1 + pages.size()) % pages.size();}

        @Override
        public void remove() {}

        @Override
        public void set(Integer integer) {}

        @Override
        public void add(Integer integer) {}
    }
}
