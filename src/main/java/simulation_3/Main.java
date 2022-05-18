package simulation_3;

import simulation_3.generators.*;
import simulation_3.replacement_algorithms.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int size = 10000;
    public static int pageNumbers = 20;
    public static int numOfFrames = 10;

    public static void main(String[] args) {
/*
        System.out.println("Number Of Frames : " + numOfFrames);
        runForNumberOfFrames(numOfFrames, new NormalGenerator(size, pageNumbers));
*/

        System.out.println("-------- NORMAL GENERATOR --------");
        System.out.println("Number Of Frames : " + 3);
        runForNumberOfFrames(3, new NormalGenerator(size, pageNumbers));
        System.out.println("Number Of Frames : " + 4);
        runForNumberOfFrames(4, new NormalGenerator(size, pageNumbers));

        System.out.println("-------- BY HAND GENERATOR --------");
        System.out.println("Number Of Frames : " + 3);
        runForNumberOfFrames(3, new ByHandGenerator(size));
        System.out.println("Number Of Frames : " + 4);
        runForNumberOfFrames(4, new ByHandGenerator(size));


    }

    public static void run(PagesManager pM, Generator g, int frames){
        System.out.println("-------------" + pM + "------------");
        List<Process_> processes = new ArrayList<>();
        Process_ p1 = new Process_(g, pM);
        processes.add(p1);
        new Simulation(processes, frames).run().printInfo(p1);
        //System.out.println();
    }

    public static void runForNumberOfFrames(int frames, Generator g){
        run(new OPT(), g, frames);
        run(new FCFS(), g, frames);
        run(new LRU(), g, frames);
        run(new APPROXIMATED_LRU(), g, frames);
        run(new RAND(), g, frames);
    }

}
