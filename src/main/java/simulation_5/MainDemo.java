package simulation_5;

import java.util.List;

public class MainDemo {

    public static void main(String[] args) {

        Demand[] demands = {new Demand(20, 70, 80),
        new Demand(60, 20, 30), new Demand(20, 0, 100)};
                //new Demand(15, 100, 0)};
        int numOfProcesses = 1000;
        double sigmaSquare = 5.0;
        int mean = 2;
        int upper = 4;

        int maxLoadFactor = 50;
        int probes = 3;

        Simulation simulation = new Simulation(new ProcessGenerator(numOfProcesses,
                new GaussProcessorDist(sigmaSquare, mean, upper).iterator(),
                new ParametrizedDemandGenerator(demands, numOfProcesses).iterator(),
                new DurationGenerator(10, 30).iterator()), new LazyStudent(maxLoadFactor, probes),
                upper + 1);

        simulation.run();
        simulation.printInfo();

    }

}
