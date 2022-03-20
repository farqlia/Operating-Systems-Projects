package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.CauchyDistribution;

public class CauchyGenerator extends Generator {

    int coeff = 2;
    CauchyDistribution bD;
    int counter;

    public CauchyGenerator(double median, double scale){
        bD = new CauchyDistribution(median, scale);
    }

    @Override
    public Process_ next() {
        return new SimpleProcess(counter++, Time.get(),
                (int)(lowerBound + SCALE * bD.cumulativeProbability(.75 * (Math.sin(NUM_GENERATOR.nextFloat())) - 0.5)));
    }

    @Override
    public int totalGenerated() {
        return 0;
    }
}
