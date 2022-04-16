package simulation_2.testing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import simulation_2.algorithms.Disc;
import simulation_2.algorithms.Request;

import javax.swing.*;
import java.awt.*;

public class PlotCylinderHeadMoves extends JFrame {

    JFreeChart chart;

    XYDataset dataset;
    XYSeries series = new XYSeries("Cyilnder Moves");

    public void initGUI(){
        dataset = new XYSeriesCollection(series);
        chart = createChart();

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15,
                15, 15, 15));
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel);

        pack();

        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private JFreeChart createChart(){

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Disc Processing",
                "Head Position",
                "Time",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        return chart;

    }

    public void addData(Disc disc){

        Request r = disc.getCurrRequest();
        if (r != null){
            series.add(disc.getCylinderHeadPosition(), disc.getNumOfCylinderHeadMoves());
        }

    }


}
