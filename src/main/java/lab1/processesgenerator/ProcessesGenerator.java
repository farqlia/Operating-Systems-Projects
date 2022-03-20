package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

import java.util.ArrayList;
import java.util.List;

public class ProcessesGenerator {

    int SCALE = 100;
    int SIZE = 1000;
    RandomGenerator generator = new Well19937c(1);
    //Random generator = new Random(1);

    // A lot of long-lasting processes that are interchanged
    // with small amount of smaller processes
    public List<SimpleProcess> generateBetaDis(){

        List<SimpleProcess> list = new ArrayList<>();
        /*
        alpha = 4, beta = 2
        alpha = 1, beta = 1 -> small task are mixed with large tasks in between

         */
        double alpha = 1;
        double beta = 1;
        BetaDistribution bD = new BetaDistribution(alpha, beta);

        for (int i = 0; i < SIZE; i++){
            // nextFloat() returns the next pseudorandom float value between 0.0 and 1.0
            list.add(new SimpleProcess(i, Time.get(),
                    (int)(SCALE * bD.cumulativeProbability(generator.nextFloat()))));

        }
        return list;
    }

    public List<SimpleProcess> generateChiSquareDis(){

        List<SimpleProcess> list = new ArrayList<>();

        /*
        k = 3 -> only processes between 1 to 20/30. so short processes
         */

        double freedomDegrees = 1;
        ChiSquaredDistribution bD = new ChiSquaredDistribution(freedomDegrees);

        for (int i = 0; i < SIZE; i++){
            // nextFloat() returns the next pseudorandom float value between 0.0 and 1.0
            list.add(new SimpleProcess(i, Time.get(),(int)(SCALE * bD.probability(generator.nextFloat()))));

        }
        return list;
    }

    // location of the median, scale > 0
    // median = 0, scale = 0.5
    // median = 0, scale = 0.2
    public List<SimpleProcess> generateCauchyDis(double median, double scale){

        List<SimpleProcess> list = new ArrayList<>();
        int coeff = 2;
        CauchyDistribution bD = new CauchyDistribution(median, scale);

        for (int i = 0; i < SIZE; i++){
            // nextFloat() returns the next pseudorandom float value between 0.0 and 1.0
            list.add(new SimpleProcess(i, Time.get(),(int)(SCALE * bD.cumulativeProbability(coeff * (0.5 - generator.nextFloat())))));

        }
        return list;
    }


    public void showValues(List<? extends Process_> processes, String name){
        System.out.println(name);
        //Comparator<SimpleProcess> comp = Comparator.comparing(SimpleProcess::getCompTime, Integer::compareTo);
        for (Process_ p : processes) {
            System.out.print(p.getCompTime() + ", ");
        }
        System.out.println();
    }

}
