package simulation_1.donotlookhere;

import simulation_1.processing.Process_;
import simulation_1.schedulers.Scheduler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class VisualizeProcesses extends JFrame {

    public VisualizeProcesses(Scheduler scheduler, int howMany){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        while (!scheduler.isDone()) {
            Process_ process = scheduler.nextProcess();
            if (process != null){
                dataset.addValue(process.getCompTime(), "", "" + process.getId());
                howMany--;
            }
        }

        JFreeChart histogram = ChartFactory.createBarChart("Processes Distribution",
                "process", "time", dataset);

        CategoryPlot plot = (CategoryPlot)histogram.getPlot();

        //addData.getRangeAxis().setRange(0, 100);

        ChartPanel chartPanel = new ChartPanel(histogram);

        add(chartPanel);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

}
