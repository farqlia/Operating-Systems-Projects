package simulation_2.simulation;

import simulation_2.algorithms.*;
import simulation_2.processgenerating.*;

import java.util.Iterator;

public class Simulation implements Iterable<Request> {

    private final int discSize;
    private final Scheduler abstractScheduler;
    private final Disc disc;

    private int numOfR = 10000;
    private int numOfPR = (int)((0.01) * numOfR);

    private int[] posGaussDistMean = {10, 190};
    private double[] deadlineGaussDistMean = {5, 0.2, 3, 0.1};

    private final boolean generatePR;

    private final Generator generator;

    public Simulation(Scheduler scheduler, int discSize, boolean generatePR){
        this.discSize = discSize;
        this.generatePR = generatePR;
        this.abstractScheduler = scheduler;
        this.disc = new Disc(abstractScheduler, discSize);
        this.generator = new PGenerator(disc, numOfR, generatePR, numOfPR, deadlineGaussDistMean, posGaussDistMean);
    }

    public int getDiscSize(){return discSize;}
    public int getTotalHeadMoves(){return disc.getNumOfHeadMoves();}

    public void run(){
         do {
            process();
            Time.t++;
        } while (disc.getNumOfRequests() != (numOfR + (generatePR ? numOfPR : 0)));
         Time.t = 0;
    }

    public void printStatistics(){

        double avgWaitingTime = ((double)disc.getWaitingTimes().stream().reduce(Integer::sum).orElse(0) / disc.getNumOfRealizedRequests());
        double maxWaitingTime = (double)disc.getWaitingTimes().stream().max(Integer::compareTo).orElse(0);
        int numOfPR = (disc.getNumOfProcessedPR() + abstractScheduler.getNumOfRejectedRequests());

        System.out.println("TOTAL CYLINDER MOVES : " + disc.getNumOfHeadMoves());
        System.out.printf("TOTAL RELATIVE CYLINDER MOVES : %.2f\n", (disc.getNumOfHeadMoves() / (double) disc.size()));
        System.out.println("NUMBER OF REQUESTS : " + (disc.getNumOfRequests()));
        System.out.println("NUMBER OF REALIZED REQUESTS : " + (disc.getNumOfRealizedRequests()));
        if (generatePR){
            System.out.println("NUMBER OF MISSED DEADLINES : " + abstractScheduler.getNumOfRejectedRequests());
            System.out.println("NUMBER OF PRIORITY REQUESTS : " + numOfPR);
            System.out.printf("PERCENTAGE OF MISSED DEADLINES : %.2f%%\n", (abstractScheduler.getNumOfRejectedRequests() / (double) numOfPR) * 100);
        }
        System.out.printf("AVERAGE WAITING TIME : %.2f\n", avgWaitingTime);
        System.out.printf("AVERAGE RELATIVE WAITING TIME : %.2f\n", (avgWaitingTime / discSize));
        System.out.printf("MAX WAITING TIME : %f\n", maxWaitingTime);
        System.out.printf("MAX RELATIVE WAITING TIME :%.2f\n", (maxWaitingTime/ discSize));

    }

    private void printInfoAboutRequest(){
        if (disc.getCurrRequest() != null) {
            System.out.println("--------------------------------------");
            System.out.println("[PROCESSING] : " + disc.getCurrRequest());
            System.out.println("--------------------------------------");
            //System.out.println("[HEAD] : " + abstractScheduler.getPosition());
            System.out.println("[HEAD MOVES] : " + disc.getNumOfHeadMoves());
            System.out.println("[TOTAL PROCESSED] : " + disc.getNumOfRealizedRequests());
        }
    }

    private void randomlyAddNewRequest(){
        if (generator.hasNext()){
            Request r = generator.next();
            if (r != null) {
                abstractScheduler.addRequest(r);
                if (PrintStatistics.print) System.out.println("[GENERATED] : " + r);
            }
        }
    }

    private void process(){
        randomlyAddNewRequest();
        if (abstractScheduler.hasRequests()) {
            disc.process();
            if (PrintStatistics.print) printInfoAboutRequest();
        }

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
