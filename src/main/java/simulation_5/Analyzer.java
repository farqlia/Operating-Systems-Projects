package simulation_5;

import simulation_5.visualization.Computation;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Analyzer{

    List<Processor> processors;
    double[] averages;
    double[] deviations;
    Computation computation;
    int timeQuanta;

    public Analyzer(List<Processor> processors, int timeQuanta){
        this.processors = processors;
        this.timeQuanta = timeQuanta;
        int n = processors.size();
        computation = new Computation(n, timeQuanta);
        averages = new double[n];
        deviations = new double[n];
    }

    public void analyze(BiConsumer<double[], double[]> consumer){analyze(null, consumer);}

    public void analyze(TrieConsumer triConsumer){analyze(triConsumer, null);}

    private void analyze(TrieConsumer triConsumer, BiConsumer<double[], double[]> consumer){
        computation.process(processors, Time.getTime());
        if (Time.getTime() > 0 && Time.getTime() % timeQuanta == 0) {
            computation.calculate(timeQuanta, averages, deviations);
            double average;
            double deviation;
            for (Processor processor : processors){
                average = averages[processor.id];
                deviation = deviations[processor.id];
                if (triConsumer != null) triConsumer.consume(processor.id, average, deviation);
            }
            if (consumer != null) consumer.accept(averages, deviations);
        }
    }

}
