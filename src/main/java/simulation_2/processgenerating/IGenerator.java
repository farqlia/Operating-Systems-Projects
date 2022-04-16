package simulation_2.processgenerating;

import simulation_2.algorithms.Request;

public interface IGenerator {

    Request next();

    int numOfGeneratedRequests();

}
