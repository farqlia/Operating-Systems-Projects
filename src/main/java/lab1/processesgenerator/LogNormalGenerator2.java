package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.LogNormalDistribution;

public class LogNormalGenerator2 extends Generator{

    LogNormalDistribution dis;
    int counter;
    double coeff = 1.5;

    public LogNormalGenerator2() {
        dis = new LogNormalDistribution();
    }

    public LogNormalGenerator2(double pam1, double pam2) {
        dis = new LogNormalDistribution(pam1, pam2);
    }

    @Override
    public Process_ next() {
        return new SimpleProcess(counter++, Time.get(),
                (int) (lowerBound + SCALE * dis.probability(Math.abs(NUM_GENERATOR.nextFloat()))));
    }

    @Override
    public int totalGenerated() {
        return 0;
    }

}
