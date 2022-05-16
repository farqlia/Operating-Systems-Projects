package simulation_3.generators;

import java.util.List;
import java.util.ListIterator;

public interface Generator {

    // By range we mean [0, range)
    int getPageRange();

    ListIterator<Integer> iterator();

    List<Integer> requests();

    // number of requests to generate
    int size();
}
