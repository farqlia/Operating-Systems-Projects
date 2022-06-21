package simulation_5.visualization;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import simulation_5.objects.Processor;

import java.util.Arrays;
import java.util.List;

public class Computation {

    private double[][] currentWorkloads;
    int timeQuanta;

    public Computation(int size, int timeQuanta){
        this.timeQuanta = timeQuanta;
        this.currentWorkloads = new double[size][timeQuanta];
    }

    public void process(List<Processor> processors, int t){
        for (Processor p : processors) currentWorkloads[p.getId()][t % timeQuanta]
                = p.currentLoad();
    }

    public void calculate(int timeDelta, double[] averages, double[] deviation){

        double sum = 0;
        int i = 0;
        for (; i < averages.length - 1; i++){
            averages[i] =  Arrays.stream(currentWorkloads[i]).sum() / timeDelta;
            sum += averages[i];
        }
        double glbAvg = (sum / (averages.length - 1));
        averages[i] = glbAvg;
        for (i = 0; i < deviation.length; i++){
            deviation[i] = Math.abs(glbAvg - averages[i]);
        }
    }

}
