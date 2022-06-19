import simulation_5.visualization.Visualizer;

import javax.swing.*;

public class VisualizerTest {

    public static void main(String[] args) {

        Visualizer visualizer = new Visualizer();

        visualizer.createSeries("Series1");

        visualizer.addToDataSeries(0, 10, 0, 0, 80, 75, 85);
        visualizer.addToDataSeries(0, 20, 0, 0, 60, 55, 65);
        visualizer.addToDataSeries(0, 30, 0, 0, 90, 85, 95);

        visualizer.createSeries("Series2");

        visualizer.addToDataSeries(1, 5, 0, 0, 60, 55, 65);
        visualizer.addToDataSeries(1, 10, 0, 0, 50, 45, 55);
        visualizer.addToDataSeries(1, 20, 0, 0, 70, 65, 75);

        visualizer.setUp();

        SwingUtilities.invokeLater(() -> visualizer.setVisible(true));

    }

}
