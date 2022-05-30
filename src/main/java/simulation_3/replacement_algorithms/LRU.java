package simulation_3.replacement_algorithms;

import simulation_3.process.Page;
import simulation_3.PrintStatistics;

import java.util.Comparator;

public class LRU extends PagesManager{

    public LRU() {
        super("LRU");
    }

    @Override
    protected void evictPage() {
        if (PrintStatistics.print) {
            //process.getPages().stream().filter(Page::isPresent).forEach(x -> System.out.print(x.getNum() + ": " + x.getReferenceTime() + " "));
            //System.out.println();
        }
        evictedPage = process.getPages().stream().filter(Page::isPresent).min(Comparator.comparingInt(Page::getReferenceTime)).get();
    }
}
