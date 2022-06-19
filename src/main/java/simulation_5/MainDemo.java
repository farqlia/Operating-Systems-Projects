package simulation_5;

import simulation_5.visualization.Computation;
import simulation_5.visualization.Visualizer;

import javax.swing.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MainDemo {

    public static void main(String[] args) {

        Demand[] demands = {new Demand(20, 70, 80),
        new Demand(60, 20, 30), new Demand(20, 0, 100)};

        int numOfProcesses = 200;
        double sigmaSquare = 5.0;

        int mean = 1;
        int upper = 3;
        int nOfProcessors = upper + 1;

        int maxLoadFactor = 70;

        int probes = 5;

        Simulation simulation = new Simulation(new ProcessGenerator(numOfProcesses,
                new GaussProcessorDist(sigmaSquare, mean, upper).iterator(),
                new ParametrizedDemandGenerator(demands, numOfProcesses).iterator(),
                new GaussianDurationGenerator(5, 25, 20, 30).iterator()),
                new ResponsibleStudent(maxLoadFactor, probes),
                nOfProcessors);

        //simulation.run();

       // simulation.printInfo();

        MainDemo mD = new MainDemo();

        mD.runAndSaveToFile("src/main/java/simulation_5/averages.txt",
                "src/main/java/simulation_5/deviations.txt",
                simulation);

    }

    private String getHeadline(int size){
        StringBuilder sB = new StringBuilder();
        sB.append("Time").append(" ");
        for (int i = 0; i < size; i++) sB.append(i).append(" ");
        return sB.toString().trim();
    }

    public void runAndSaveToFile(String avgFileName, String devFileName, Simulation simulation){
        WriteToFile wTFAverages = new WriteToFile();
        WriteToFile wTFDeviations = new WriteToFile();

        String headLine = getHeadline(simulation.getProcessors().size());

        wTFAverages.addHeaders(headLine);
        wTFDeviations.addHeaders(headLine);

        BiConsumer<double[], double[]> saveToFileConsumer = (avgs, devs) -> {
            wTFAverages.parseToString(avgs);
            wTFDeviations.parseToString(devs);};

        runAndSaveToFile(saveToFileConsumer, simulation);

        wTFAverages.writeToFile(avgFileName);
        wTFDeviations.writeToFile(devFileName);

    }

    public void runAndSaveToFile(BiConsumer<double[], double[]> consumer, Simulation simulation){

        Analyzer analyzer = new Analyzer(simulation.getProcessors(), 10);

        Simulation.Runner runner = simulation.new Runner();

        while (runner.hasNext()) {
            runner.next();
            analyzer.analyze(consumer);
            if (Time.getTime() > 0 && Time.getTime() % 10 == 0) {
                simulation.printProcessorsOverload();
            }
            Time.incr();
        }

    }

    public void runAndVisualize(Simulation simulation){

        Visualizer visualizer = new Visualizer();

        for (Processor processor : simulation.getProcessors())
            visualizer.createSeries("Processor " + processor.id);

        Analyzer analyzer = new Analyzer(simulation.getProcessors(), 10);

        Simulation.Runner runner = simulation.new Runner();

        TrieConsumer tC = (x, y, z) -> visualizer.addToDataSeries(x, Time.getTime(), 0, 0,
                y, y - z, y + z);

        while (runner.hasNext()) {
            runner.next();
            analyzer.analyze(tC);
            if (Time.getTime() > 0 && Time.getTime() % 10 == 0) {
                simulation.printProcessorsOverload();
            }
            Time.incr();
        }

        visualizer.setUp();
        SwingUtilities.invokeLater(() -> visualizer.setVisible(true));
    }

}
