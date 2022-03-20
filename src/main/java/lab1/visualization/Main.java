package lab1.visualization;

import lab1.processesgenerator.CauchyGenerator;
import lab1.processesgenerator.ChiSquatred;
import lab1.processesgenerator.Generator;
import lab1.processesgenerator.ProcessesGenerator2;
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
        Generator g = new ProcessesGenerator2(40, 10, 2, 0.3, 100, s);
        int itr = 100;
        while (g.totalGenerated() < itr) g.next();

        SwingUtilities.invokeLater(() ->
              new VisualizeProcesses(s, 100));

        /*
        Process_ p;
        while ((p = s.nextProcess()) != null) {
            System.out.println(p.getCompTime());
        }


         */


    }

}
