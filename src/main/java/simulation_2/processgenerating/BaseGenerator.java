package simulation_2.processgenerating;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;

public class BaseGenerator implements Generator {

    private final RandomGenerator ACCESS_GENERATOR = new Well19937c(1);

    private final Disc disc;
    private final Producer positionProducer;
    private final Producer deadlineProducer;

    private final int totalRequests;

    private final int threshold = 10;

    private double currentChance = -0.005;
    private int generatedRequests;

    public BaseGenerator(Disc disc, int totalRequests, Producer positionProducer, Producer deadlineProducer) {
        this.disc = disc;
        this.totalRequests = totalRequests;
        this.deadlineProducer = deadlineProducer;
        this.positionProducer = positionProducer;
    }


    private Request produceRequest(){
        int position = (int)positionProducer.produce();
        int deadline = (int)(deadlineProducer.produce()
                * Math.abs(position - disc.getHeadPosition()));
        return new Request(position, disc.getNumOfHeadMoves(), deadline);
    }

    @Override
    public Request next() {
        currentChance *= (-1);
        if (generatedRequests < totalRequests && ((generatedRequests - disc.getNumOfRequests()) / (double) threshold <= ACCESS_GENERATOR.nextFloat() + currentChance||
                generatedRequests - disc.getNumOfRequests() <= 1)){
            generatedRequests++;
            return produceRequest();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        return generatedRequests != totalRequests;
    }
}
