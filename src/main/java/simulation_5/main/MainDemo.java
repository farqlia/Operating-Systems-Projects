package simulation_5.main;

import simulation_5.dataprocessing.Analyzer;
import simulation_5.dataprocessing.TrieConsumer;
import simulation_5.dataprocessing.WriteToFile;
import simulation_5.generators.GaussProcessorDist;
import simulation_5.generators.GaussianDurationGenerator;
import simulation_5.generators.ParametrizedDemandGenerator;
import simulation_5.generators.ProcessGenerator;
import simulation_5.migrationstrategies.HelpfulStudent;
import simulation_5.migrationstrategies.LazyStudent;
import simulation_5.migrationstrategies.MigrationStrategy;
import simulation_5.migrationstrategies.ResponsibleStudent;
import simulation_5.generators.Demand;
import simulation_5.objects.Processor;
import simulation_5.visualization.Visualizer;

import javax.swing.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MainDemo {

    int nOfProcessors = 20;
    String pathToFolder = "src/main/resources/results/";

    public ProcessGenerator getGenerator(){

        Demand[] demands = {new Demand(1, 70, 80, 30),
                new Demand(99, 3, 15, 70),
        };

        int numOfProcesses = 10000;

        return new ProcessGenerator(numOfProcesses,
                new GaussProcessorDist(nOfProcessors),
                new ParametrizedDemandGenerator(demands, numOfProcesses),
                new GaussianDurationGenerator(5, 20, 40));
    }

    public void runSimulation(MigrationStrategy migrationStrategy, String folderName){

        Time.reset();

        Simulation simulation = new Simulation(getGenerator(),
                migrationStrategy, nOfProcessors);

        runAndSaveToFile(folderName, simulation);
    }

    public void runHelpfulStudent(){
        int maxLoadFactor = 80;
        int minLoadFactor = 20;

        int communicationFrequency = 10;

        MigrationStrategy migrationStrategy = new HelpfulStudent(maxLoadFactor,
                minLoadFactor, communicationFrequency);

        runSimulation(migrationStrategy, "helpfulstudent");
    }

    public void runResponsibleStudent(){
        int maxLoadFactor = 80;
        int probes = 5;

        MigrationStrategy migrationStrategy = new ResponsibleStudent(maxLoadFactor, probes);

        runSimulation(migrationStrategy, "responsiblestudent");
    }

    public void runLazyStudent(){
        int maxLoadFactor = 80;
        int probes = 5;

        MigrationStrategy migrationStrategy = new LazyStudent(maxLoadFactor, probes);

        runSimulation(migrationStrategy, "lazystudent");
    }

    public static void main(String[] args) {

        MainDemo mainDemo = new MainDemo();

        mainDemo.runLazyStudent();

        mainDemo.runResponsibleStudent();

        mainDemo.runHelpfulStudent();

    }

    public void runAndSaveToFile(String folderName, Simulation simulation){
        WriteToFile writer = new WriteToFile();

        writer.clearFile(pathToFolder + folderName + "/averages.csv");
        writer.clearFile(pathToFolder + folderName + "/deviations.csv");
        writer.clearFile(pathToFolder + folderName + "/inmigrations.csv");
        writer.clearFile(pathToFolder + folderName + "/outmigrations.csv");
        writer.clearFile(pathToFolder + folderName + "/communications.csv");
        writer.clearFile(pathToFolder + folderName + "/numofprocesses.csv");

        runAndSaveToFile(simulation, 10,
                a -> writer.writeToCSV(a.getAverages(), pathToFolder + folderName + "/averages.csv"),
                a -> writer.writeToCSV(a.getDeviations(),pathToFolder + folderName + "/deviations.csv"),
                a -> writer.writeToCSV(a.getInGoingMigrations(),pathToFolder + folderName + "/inmigrations.csv"),
                a -> writer.writeToCSV(a.getOutGoingMigrations(),pathToFolder + folderName + "/outmigrations.csv"),
                a -> writer.writeToCSV(a.getCommunications(),pathToFolder + folderName + "/communications.csv"),
                a -> writer.writeToCSV(a.getNumOfProcesses(),pathToFolder + folderName + "/numofprocesses.csv"));

    }

    @SafeVarargs
    public final void runAndSaveToFile(Simulation simulation, int timeQuanta, Consumer<Analyzer>... consumers){

        Analyzer analyzer = new Analyzer(simulation.getProcessors(), timeQuanta);

        Simulation.Runner runner = simulation.new Runner();

        while (runner.hasNext()) {
            runner.next();
            analyzer.analyze();
            if (Time.getTime() > 0 && Time.getTime() % timeQuanta == 0) {
                for (Consumer<Analyzer> consumer : consumers) consumer.accept(analyzer);
                if (PrintStatistics.print) simulation.printProcessorsOverload();
            }
            Time.incr();
        }

    }

    public void runAndSaveToFile(BiConsumer<double[], double[]> biConsumer, Consumer<Integer> consumer,
                                 Simulation simulation, int timeQuanta){

        Analyzer analyzer = new Analyzer(simulation.getProcessors(), timeQuanta);

        Simulation.Runner runner = simulation.new Runner();

        while (runner.hasNext()) {
            runner.next();
            analyzer.analyze(biConsumer);
            if (Time.getTime() > 0 && Time.getTime() % timeQuanta == 0) {

                consumer.accept(simulation.getMigrationStrategy().getMigrationsCount());
                simulation.printProcessorsOverload();
            }
            Time.incr();
        }

    }

    public void runSimulation(MigrationStrategy migrationStrategy){

        Simulation simulation = new Simulation(getGenerator(),
                migrationStrategy, nOfProcessors);

        Simulation.Runner runner = simulation.new Runner();

        while (runner.hasNext()) {
            runner.next();
            if (Time.getTime() > 0 && Time.getTime() % 10 == 0) {
                simulation.printProcessorsOverload();
            }
            Time.incr();
        }

    }

    public void runAndVisualize(Simulation simulation, int timeQuanta){

        Visualizer visualizer = new Visualizer();

        for (Processor processor : simulation.getProcessors())
            visualizer.createSeries("Processor " + processor.id);

        Analyzer analyzer = new Analyzer(simulation.getProcessors(), timeQuanta);

        Simulation.Runner runner = simulation.new Runner();

        TrieConsumer tC = (x, y, z) -> visualizer.addToDataSeries(x, Time.getTime(), 0, 0,
                y, y - z, y + z);

        while (runner.hasNext()) {
            runner.next();
            analyzer.analyze(tC);
            if (Time.getTime() > 0 && Time.getTime() % timeQuanta == 0) {
                simulation.printProcessorsOverload();
            }
            Time.incr();
        }

        visualizer.setUp();
        SwingUtilities.invokeLater(() -> visualizer.setVisible(true));
    }

}
