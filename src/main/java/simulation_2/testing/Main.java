package simulation_2.testing;

import simulation_2.algorithms.*;
import simulation_2.simulation.Simulation;
import simulation_2.strategies.EDF;
import simulation_2.strategies.FD_SCAN;

public class Main {

    private int discSize = 200;

    private void run(Scheduler scheduler, boolean gPR, String algName){
        System.out.println("---------" + algName + "------------");
        Simulation simulation = new Simulation(scheduler, discSize, gPR);
        simulation.run();
        simulation.printStatistics();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
        System.out.println();
        main.runEDFs();
        System.out.println();
        main.runFD_SCANs();
    }

    private void run(){
        run(new FCFS(), false, "FCFS");
        run(new SSTF(), false, "SSTF");
        run(new SCAN(discSize), false,"SCAN");
        run(new C_SCAN(discSize), false,"C_SCAN");
    }

    private void runEDFs(){
        System.out.println("-------- EDFs ------------");
        boolean gPR = true;
        run(new EDF(new FCFS()), gPR, "FCFS");
        run(new EDF(new SSTF()), gPR, "SSTF");
        run(new EDF(new SCAN(discSize)), gPR,"SCAN");
        run(new EDF(new C_SCAN(discSize)), gPR,"C_SCAN");
    }

    private void runFD_SCANs(){
        System.out.println("-------- FD_SCANs ------------");
        boolean gPR = true;

        run(new FD_SCAN(new FCFS(), discSize), gPR, "FCFS");
        run(new FD_SCAN(new SSTF(), discSize), gPR, "SSTF");
        run(new FD_SCAN(new SCAN(discSize), discSize), gPR,"SCAN");
        run(new FD_SCAN(new C_SCAN(discSize), discSize), gPR,"C_SCAN");
    }

}
