package simulation_2.simulation;

import simulation_2.algorithms.Disc;
import simulation_2.algorithms.FCFS;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.AbstractScheduler;
import simulation_2.processgenerating.Generator;
import simulation_2.strategies.EDF;

import java.util.Iterator;

public class Simulation implements Iterable<Request> {

    private final AbstractScheduler abstractScheduler = new EDF(new FCFS());
    private final Disc disc = new Disc(abstractScheduler);

    private boolean generatePriorityRequests = true;
    private double percentageOfPriorityRequests = 0.1;
    private int deadline = 20;
    private double chanceForDeadline = 1;
    private int discSize = 100;
    private boolean gaussDist = true;
    private int[] gaussDistMean = {10, 50, 90};
    private int totalRequests = 10;

    private final Generator generator =
            new Generator(disc, totalRequests, generatePriorityRequests, percentageOfPriorityRequests,
                    deadline, chanceForDeadline, discSize, gaussDist, gaussDistMean);

    public int getDiscSize(){return discSize;}
    public int getTotalHeadMoves(){return disc.getNumOfCylinderHeadMoves();}

    public void run(){
        while (disc.getNumOfRequests() != totalRequests){
            process();
        }
    }

    public void printStatistics(){

        System.out.println("TOTAL CYLINDER MOVES : " + disc.getNumOfCylinderHeadMoves());
        System.out.println("NUMBER OF REQUESTS : " + disc.getNumOfRequests());
        System.out.println("NUMBER OF MISSED DEADLINES : " + disc.getNumOfMissedDeadlines());
        System.out.printf("AVERAGE WAITING TIMES : %.2f\n", ((double)disc.getWaitingTimes().stream().reduce(Integer::sum).orElse(0) / disc.getNumOfRequests()));

    }

    private void printInfoAboutRequest(){
        if (disc.getCurrRequest() != null) {
            System.out.println("--------------------------------------");
            System.out.println("[PROCESSING] : " + disc.getCurrRequest());
            System.out.println("--------------------------------------");
            System.out.println("[CYLINDER POSITION] : " + abstractScheduler.getPosition());
            System.out.println("[CYLINDER MOVES] : " + disc.getNumOfCylinderHeadMoves());
            System.out.println("[TOTAL PROCESSED] : " + disc.getNumOfRequests());
        }
    }

    private void randomlyAddNewRequest(){
        if (generator.numOfGeneratedRequests() != totalRequests){
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
            return disc.getNumOfRequests() != totalRequests;
        }

        @Override
        public Request next() {
            randomlyAddNewRequest();
            disc.process();
            return disc.getCurrRequest();    // this can be null
        }
    }

}
