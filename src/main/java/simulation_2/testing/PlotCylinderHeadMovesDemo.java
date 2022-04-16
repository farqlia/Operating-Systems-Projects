package simulation_2.testing;

import simulation_2.algorithms.Disc;
import simulation_2.algorithms.FCFS;
import simulation_2.algorithms.Request;
import simulation_2.algorithms.AbstractScheduler;
import simulation_2.processgenerating.Generator;

import javax.swing.*;

public class PlotCylinderHeadMovesDemo {

    private static final AbstractScheduler ABSTRACT_SCHEDULER = new FCFS();
    private static final Disc disc = new Disc(ABSTRACT_SCHEDULER);

    private static boolean generatePriorityRequests = true;
    private static int freqOfPriorityReq = 10;
    private static int deadline = 20;
    private static double chance = 1;
    private static int discSize = 10;
    private static boolean gaussDist = false;
    private static int[] gaussDistMean = {};
    private static int totalRequests = 10;

    private static final PlotCylinderHeadMoves plot = new PlotCylinderHeadMoves();

    // najdalsze możliwe do wykonania

    private static final Generator generator =
            new Generator(disc, totalRequests, generatePriorityRequests, freqOfPriorityReq,
                    deadline, chance, discSize, gaussDist, gaussDistMean);


    public static void main(String[] args) {

        while (disc.getNumOfRequests() != totalRequests){
            if (generator.numOfGeneratedRequests() != totalRequests){
                Request r = generator.next();
                if (r != null) {
                    ABSTRACT_SCHEDULER.addRequest(r);
                }
            }
            disc.process();
            plot.addData(disc);
        }

        SwingUtilities.invokeLater(plot::initGUI);

    }


}
