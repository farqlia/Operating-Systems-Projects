package simulation_3;

import simulation_3.generators.ByHandGenerator;
import simulation_3.generators.LoopingGenerator;
import simulation_3.generators.NormalGenerator;
import simulation_3.generators.Simulation;
import simulation_3.replacement_algorithms.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static int size = 1000;
    public static void main(String[] args) {
        System.out.println("Number Of Frames : " + 3);
        runForNumberOfFrames(3);
        System.out.println();
        System.out.println("Number Of Frames : " + 4);
        runForNumberOfFrames(4);
    }

    public static void run(PagesManager pM, int frames){
        System.out.println("-------------" + pM + "------------");
        List<Process_> processes = new ArrayList<>();
        Process_ p1 = new Process_(new ByHandGenerator(size), pM);
        processes.add(p1);
        new Simulation(processes, frames).run().printInfo(p1);
        System.out.println();
    }

    public static void runForNumberOfFrames(int frames){
        run(new OPT(), frames);
        run(new FCFS(), frames);
        run(new LRU(), frames);
        run(new APPROXIMATED_LRU(), frames);
        run(new RAND(), frames);
    }

}
