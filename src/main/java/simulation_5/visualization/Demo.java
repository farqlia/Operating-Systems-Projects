package simulation_5.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.*;
import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Demo().setVisible(true));
    }

    public Demo(){
        initUI();
    }

    private void initUI(){

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        chartPanel.setBackground(Color.WHITE);
        add(chartPanel);

        pack();

        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset(){
        XYIntervalSeries intervalSeries = new XYIntervalSeries("2014");
        intervalSeries.add(100, 80, 120, 500, 480, 520);

        XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();
        dataset.addSeries(intervalSeries);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset){
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Average salary per age",
                "Age",
                "Salary (€)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYErrorRenderer renderer = new XYErrorRenderer();
        renderer.findRangeBounds(dataset);
        renderer.setDrawYError(true);
        renderer.setDrawXError(true);

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.gray);

        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Average Salary per Age",
                new Font("Serif", java.awt.Font.BOLD, 18)
        ));

        return chart;
    }


}
