package simulation_2.testing;

import simulation_2.algorithms.Request;
import simulation_2.simulation.Simulation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class Visualizer {

    private final Simulation simulation;
    private final JFrame frame;

    private final List<Request> requestList;

    public Visualizer(Simulation simulation){
        this.simulation = simulation;
        this.requestList = new ArrayList<>();
        prepareRequests();
        frame = new PlotCylinderHeadMoves(requestList, simulation.getDiscSize(),
                simulation.getTotalHeadMoves(), 1000, 800);
    }

    private void prepareRequests(){

        for (Request request : simulation){
            if (request != null){
                requestList.add(request);
            }
        }
    }

    public void show(){
        frame.setVisible(true);
    }

}
