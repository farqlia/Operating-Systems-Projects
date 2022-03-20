package lab1.processesgenerator;

import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Time;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class ChiSquatred extends Generator {

    ChiSquaredDistribution dis;
    int count;

    public ChiSquatred(int k){
        dis = new ChiSquaredDistribution(k);
    }

    public Process_ next(){
        return new
                SimpleProcess(count++, Time.get(), 1 + (int)(100 * dis.cumulativeProbability(9 * NUM_GENERATOR.nextFloat())));
    }

    @Override
    public int totalGenerated() {
        return 0;
    }

}
