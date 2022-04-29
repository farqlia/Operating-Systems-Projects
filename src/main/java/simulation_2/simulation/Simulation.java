package simulation_2.simulation;

import simulation_2.algorithms.Disc;
import simulation_2.algorithms.FCFS;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.AbstractScheduler;
import simulation_2.processgenerating.*;
import simulation_2.strategies.EDF;

import java.util.Iterator;

public class Simulation implements Iterable<Request> {

    private int discSize = 100;
    private final AbstractScheduler abstractScheduler = new EDF(new FCFS());
    private final Disc disc = new Disc(abstractScheduler, discSize);

    private double percentageOfPriorityRequests = 0.1;
    private int deadline = 20;
    private int[] posGaussDistMean = {10, 50, 90};
    private double[] deadlineGaussDistMean = {0.2, 5, 1};
    private int totalRequests = 1000;

    private final Producer positionProducer = new PositionProducer(discSize, posGaussDistMean);
    private final Producer deadlineProducer = new DeadlineProducer(totalRequests, deadline,
            deadlineGaussDistMean, true, percentageOfPriorityRequests);

    private final IGenerator generator = new BaseGenerator(disc, totalRequests,
            positionProducer, deadlineProducer);

    public int getDiscSize(){return discSize;}
    public int getTotalHeadMoves(){return disc.getNumOfHeadMoves();}

    public void run(){
        do {
            process();
        }
        while (abstractScheduler.hasRequests());
    }

    public void printStatistics(){

        double avgWaitingTime = ((double)disc.getWaitingTimes().stream().reduce(Integer::sum).orElse(0) / disc.getNumOfRealizedRequests());

        System.out.println("TOTAL CYLINDER MOVES : " + disc.getNumOfHeadMoves());
        System.out.println("NUMBER OF REQUESTS : " + disc.getNumOfRealizedRequests());
        System.out.println("NUMBER OF MISSED DEADLINES : " + abstractScheduler.getNumOfRejectedRequests());
        System.out.println("NUMBER OF PRIORITY REQUESTS : " + (disc.getNumOfProcessedPR() + abstractScheduler.getNumOfRejectedRequests()));
        System.out.printf("AVERAGE WAITING TIME : %.2f\n", avgWaitingTime);
        System.out.printf("AVERAGE RELATIVE WAITING TIME : %.2f\n", (avgWaitingTime / disc.getNumOfHeadMoves()) * 100);

    }

    private void printInfoAboutRequest(){
        if (disc.getCurrRequest() != null) {
            System.out.println("--------------------------------------");
            System.out.println("[PROCESSING] : " + disc.getCurrRequest());
            System.out.println("--------------------------------------");
            System.out.println("[HEAD] : " + abstractScheduler.getPosition());
            System.out.println("[HEAD MOVES] : " + disc.getNumOfHeadMoves());
            System.out.println("[TOTAL PROCESSED] : " + disc.getNumOfRealizedRequests());
        }
    }

    private void randomlyAddNewRequest(){
        if (generator.hasNext()){
            Request r = generator.next();
            if (r != null) {
                abstractScheduler.addRequest(r);
                System.out.println("[GENERATED] : " + r);
                System.out.println("[TOTAL GENERATED] : " + generator.numOfGeneratedRequests());
            }
        }
    }

    private void process(){
        randomlyAddNewRequest();
        disc.process();
        printInfoAboutRequest();
    }

    @Override
    public Iterator<Request> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Request>{

        @Override
        public boolean hasNext() {
            randomlyAddNewRequest();
            return abstractScheduler.hasRequests();
        }

        @Override
        public Request next() {
            disc.process();
            return disc.getCurrRequest();    // this can be null
        }
    }

}
