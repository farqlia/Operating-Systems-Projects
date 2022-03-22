package lab1.visualization;

import lab1.processesgenerator.*;
import lab1.processing.Process_;
import lab1.schedulers.Scheduler;
import lab1.schedulers.TrapScheduler;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        /*

        SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(new CauchyGenerator(0, 0.5), 100));

                SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(, 100));

                Long processes are mixed with short processes
                SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(new LogNormalGenerator(), 100));

                SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(new LogNormalGenerator(0, 1.5), 100));

                // Values are between 40-60
                SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(new LogNormalGenerator(0, 3), 100));

                SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(new LogNormalGenerator(-0.5, 1.5), 100));

                // Short processes
                SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(new LogNormalGenerator(0, 5), 100));

                SwingUtilities.invokeLater(() ->
                new VisualizeProcesses(new ChiSquatred(4), 200));
         */

        Scheduler s = new TrapScheduler();
        int itr = 300;
        //Generator g = new ProcessesGenerator2(40, 10, 2, 0.3, 100, s);
        //Generator g = new SJFKillGenerator(20, 50, 1, 0.3, itr,0.5, 0, 0.1, 5 , s);
        Generator g = new ProcessesGenerator(5, 20, 1, itr, s);
        while (g.totalGenerated() < itr) g.next();

        SwingUtilities.invokeLater(() ->
              new VisualizeProcesses(s, itr));

        /*
        Process_ p;
        while ((p = s.nextProcess()) != null) {
            System.out.println(p.getCompTime());
        }


         */


    }

}
