package simulation_2.simulation;

import simulation_2.algorithms.Disc;
import simulation_2.algorithms.FCFS;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.AbstractScheduler;
import simulation_2.processgenerating.Generator;
import simulation_2.strategies.EDF;

public class Simulation {

    private final AbstractScheduler abstractScheduler = new EDF(new FCFS());
    private final Disc disc = new Disc(abstractScheduler);

    private boolean generatePriorityRequests = true;
    private double percentageOfPriorityRequests = 0.1;
    private int deadline = 20;
    private double chanceForDeadline = 1;
    private int discSize = 100;
    private boolean gaussDist = true;
    private int[] gaussDistMean = {10, 50, 90};
    private int totalRequests = 1000;

    private final Generator generator =
            new Generator(disc, totalRequests, generatePriorityRequests, percentageOfPriorityRequests,
                    deadline, chanceForDeadline, discSize, gaussDist, gaussDistMean);

    public void run(){
        while (disc.getNumOfRequests() != totalRequests){
            if (generator.numOfGeneratedRequests() != totalRequests){
                Request r = generator.next();
                if (r != null) {
                    abstractScheduler.addRequest(r);
                    System.out.println("[GENERATED] : " + r);
                    System.out.println("[TOTAL GENERATED] : " + generator.numOfGeneratedRequests());
                }
            }
            disc.process();
            if (disc.getCurrRequest() != null) {
                System.out.println("--------------------------------------");
                System.out.println("[PROCESSING] : " + disc.getCurrRequest());
                System.out.println("--------------------------------------");
                System.out.println("[CYLINDER POSITION] : " + abstractScheduler.getPosition());
                System.out.println("[CYLINDER MOVES] : " + disc.getNumOfCylinderHeadMoves());
                System.out.println("[TOTAL PROCESSED] : " + disc.getNumOfRequests());
            }
            //

        }
    }

    public void printStatistics(){

        System.out.println("TOTAL CYLINDER MOVES : " + disc.getNumOfCylinderHeadMoves());
        System.out.println("NUMBER OF REQUESTS : " + disc.getNumOfRequests());
        System.out.println("NUMBER OF MISSED DEADLINES : " + disc.getNumOfMissedDeadlines());
        System.out.printf("AVERAGE WAITING TIMES : %.2f\n", ((double)disc.getWaitingTimes().stream().reduce(Integer::sum).orElse(0) / disc.getNumOfRequests()));

    }

}
