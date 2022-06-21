package simulation_5.dataprocessing;

import simulation_5.main.Time;
import simulation_5.objects.Processor;
import simulation_5.visualization.Computation;

import java.util.List;
import java.util.function.BiConsumer;

public class Analyzer{

    List<Processor> processors;
    double[] averages;
    double[] deviations;
    int[] inGoingMigrations;
    int[] outGoingMigrations;
    int[] communications;
    int[] numOfProcesses;
    Computation computation;
    int timeQuanta;

    public Analyzer(List<Processor> processors, int timeQuanta){
        this.processors = processors;
        this.timeQuanta = timeQuanta;
        int n = processors.size();
        computation = new Computation(n, timeQuanta);
        averages = new double[n + 1];
        deviations = new double[n];
        this.inGoingMigrations = new int[n];
        this.outGoingMigrations = new int[n];
        this.communications = new int[n];
        this.numOfProcesses = new int[n];
    }

    public void analyze(BiConsumer<double[], double[]> consumer){
        analyze(null, consumer);
    }

    public void analyze(TrieConsumer triConsumer){analyze(triConsumer, null);}


    public void analyze(){
        computation.process(processors, Time.getTime());
        if (Time.getTime() > 0 && Time.getTime() % timeQuanta == 0) {
            computation.calculate(timeQuanta, averages, deviations);
            for (Processor processor : processors){
                gatherData(processor);
            }
        }
    }

    private void analyze(TrieConsumer triConsumer, BiConsumer<double[], double[]> consumer){
        computation.process(processors, Time.getTime());
        if (Time.getTime() > 0 && Time.getTime() % timeQuanta == 0) {
            computation.calculate(timeQuanta, averages, deviations);
            double average;
            double deviation;
            for (Processor processor : processors){
                gatherData(processor);
                average = averages[processor.id];
                deviation = deviations[processor.id];
                if (triConsumer != null) triConsumer.consume(processor.id, average, deviation);
            }
            if (consumer != null) consumer.accept(averages, deviations);
        }
    }

    private void gatherData(Processor processor){
        inGoingMigrations[processor.id] = processor.getInComingMigrations();
        outGoingMigrations[processor.id] = processor.getOutGoingMigrations();
        communications[processor.id] = processor.getCommunications();
        numOfProcesses[processor.id] = processor.numOfProcesses();
        processor.resetCommunications();
        processor.resetInComingMigrations();
        processor.resetOutGoingMigrations();
    }

    public double[] getAverages() {
        return averages;
    }

    public double[] getDeviations() {
        return deviations;
    }

    public int[] getInGoingMigrations() {
        return inGoingMigrations;
    }

    public int[] getOutGoingMigrations() {
        return outGoingMigrations;
    }

    public int[] getCommunications() {
        return communications;
    }

    public int[] getNumOfProcesses() {
        return numOfProcesses;
    }
}
