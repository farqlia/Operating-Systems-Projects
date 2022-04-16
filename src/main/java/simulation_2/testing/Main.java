package simulation_2.testing;

import simulation_2.simulation.Simulation;

public class Main {

    public static void main(String[] args) {

        Simulation simulation = new Simulation();
        simulation.run();
        simulation.printStatistics();

    }

}
