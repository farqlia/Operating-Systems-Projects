package lab1.visualization;

import lab1.processesgenerator.Generator;
import lab1.processing.Process_;
import lab1.processing.SimpleProcess;
import lab1.schedulers.Scheduler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

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

        //plot.getRangeAxis().setRange(0, 100);

        ChartPanel chartPanel = new ChartPanel(histogram);

        add(chartPanel);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

}
