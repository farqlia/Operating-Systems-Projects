package simulation_2.processgenerating;

import simulation_2.algorithms.Request;

public interface Generator {

    Request next();

    boolean hasNext();

}
