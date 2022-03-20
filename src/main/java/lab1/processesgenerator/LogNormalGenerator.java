package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.random.GaussianRandomGenerator;

public class LogNormalGenerator extends Generator{

    LogNormalDistribution dis;
    int counter;
    double coeff = 1.5;

    public LogNormalGenerator() {
        dis = new LogNormalDistribution();
    }

    public LogNormalGenerator(double pam1, double pam2) {
        dis = new LogNormalDistribution(pam1, pam2);
    }

    @Override
    public Process_ next() {
        return new SimpleProcess(counter++, Time.get(),
                (int) (lowerBound + SCALE * dis.cumulativeProbability(coeff * Math.abs(NUM_GENERATOR.nextGaussian()))));
    }

    @Override
    public int totalGenerated() {
        return 0;
    }
}
