package simulation_2.processgenerating;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;

public class BaseGenerator implements IGenerator{

    private final RandomGenerator ACCESS_GENERATOR = new Well19937c(1);

    private final Disc disc;
    private final Producer positionProducer;
    private final Producer deadlineProducer;

    private final int totalRequests;
    private final double chance = 0.001;

    private double currentChance = 0.005;
    private int generatedRequests;
    private long sumOfPaths;

    public BaseGenerator(Disc disc, int totalRequests, Producer positionProducer, Producer deadlineProducer) {
        this.disc = disc;
        this.totalRequests = totalRequests;
        this.deadlineProducer = deadlineProducer;
        this.positionProducer = positionProducer;
    }

    protected long getSumOfPaths(){return sumOfPaths;}

    private Request produceRequest(){
        int position = (int)positionProducer.produce();
        int deadline = (int)(deadlineProducer.produce()
                * Math.abs(position - disc.getHeadPosition()));
        sumOfPaths += position;
        return new Request(position, disc.getNumOfHeadMoves(), deadline);
    }

    @Override
    public Request next() {
        if (generatedRequests < totalRequests && ((double) sumOfPaths / (disc.getNumOfHeadMoves() + 1) <= ACCESS_GENERATOR.nextFloat() + currentChance||
                generatedRequests - disc.getNumOfRequests() <= 1)){
            generatedRequests++;
            Request request = produceRequest();
            currentChance *= (-1);
            return request;
        } else {
            currentChance *= (-1);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return generatedRequests != totalRequests;
    }

    @Override
    public int numOfGeneratedRequests() {
        return generatedRequests;
    }
}
