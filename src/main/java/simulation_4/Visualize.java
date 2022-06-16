package simulation_4;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import simulation_3.process.Process_;
import simulation_3.replacement_algorithms.PagesManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Visualize extends JFrame {

    XYSeriesCollection dataset = new XYSeriesCollection();
    int delta = 5;

    public Visualize(){

    }

    private void init(){
        setSize(1000, 1000);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "PFF", "time", "pff", dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);

        setLayout(new BorderLayout());
        add(chartPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void visualize(List<Process_> processes){
        for (Process_ p : processes){
            addData(p.getId(), p.getPagesManager());
        }
        showPlot();
    }

    public void addData(int id, PagesManager pgM){
        XYSeries series = new XYSeries(id);
        int counter = 0;
        for (int i = pgM.getCount() - 1 - delta; i >=0; i--){
            series.add(counter++, (pgM.getPastMissCount(i) - pgM.getPastMissCount(i + delta)));
        }
        dataset.addSeries(series);
    }

    public void showPlot(){
        init();
        setVisible(true);
    }

}
