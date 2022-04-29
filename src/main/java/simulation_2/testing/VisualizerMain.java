package simulation_2.testing;

import simulation_2.simulation.Simulation;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class VisualizerMain {

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        Visualizer visualizer = new Visualizer(simulation);
        SwingUtilities.invokeLater(visualizer::show);
    }
}
