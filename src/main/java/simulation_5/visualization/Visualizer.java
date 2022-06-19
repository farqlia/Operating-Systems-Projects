package simulation_5.visualization;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Visualizer extends JFrame {

    List<XYIntervalSeries> series;
    InnerLegendSource lIS;
    XYPlot plot;

    public Visualizer(){
        this.series = new ArrayList<>();
        lIS = new InnerLegendSource();
        plot = new XYPlot();
    }

    public void setUp(){

        JFreeChart chart = createChart(prepareCollection());

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel);
        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void plotStandardDeviation(XYDataset dataset){
        plot.setDataset(1, dataset);
        XYErrorRenderer renderer = getErrorsRenderer();
        plot.setRenderer(1, renderer);
        colorRenderer(renderer);
    }

    public void plotData(XYDataset dataset){
        plot.setDataset(0, dataset);
        XYLineAndShapeRenderer renderer = getDataRenderer();
        plot.setRenderer(0, renderer);
        colorRenderer(renderer);
    }

    public JFreeChart createChart(XYDataset dataset){

        setUpPlot(dataset);

        JFreeChart chart = new JFreeChart(plot);

        chart.getLegend().setSources(new LegendItemSource[]{lIS});

        chart.getLegend().setFrame(BlockBorder.NONE);

        return chart;
    }

    private void setUpPlot(XYDataset dataset){

        colorSeries();

        plotData(dataset);
        //plotStandardDeviation(dataset);

        plot.setRangeAxis(createYAxis(dataset));
        plot.setDomainAxis(createXAxis(dataset));

        plot.setBackgroundPaint(new Color(173, 173, 173));

        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);
    }

    private static class InnerLegendSource implements LegendItemSource{

        private final LegendItemCollection lIC;

        public InnerLegendSource(){lIC = new LegendItemCollection();}

        @Override
        public LegendItemCollection getLegendItems() {
            return lIC;
        }
    }

    private ValueAxis createXAxis(XYDataset dataset){
        ValueAxis vX = new NumberAxis();
       // vX.setUpperMargin(20);
        vX.setRange(0, 10 * dataset.getSeriesCount());
        return vX;
    }

    private ValueAxis createYAxis(XYDataset dataset){
        ValueAxis vY = new NumberAxis();
       // vY.setUpperMargin(20);
        vY.setRange(0, 300);
        return vY;
    }

    private void colorRenderer(XYItemRenderer renderer){
        for (int i = 0; i < series.size(); i++){
            renderer.setSeriesPaint(i, lIS.lIC.get(i).getFillPaint());
            renderer.setSeriesStroke(i, new BasicStroke(2.0f));
        }
    }

    private void colorSeries(){
        for (XYIntervalSeries xyIntervalSeries : series) {
            Color c = createRGBColor();
            lIS.lIC.add(new LegendItem(xyIntervalSeries.getKey().toString(), c));
        }
    }

    private Color createRGBColor(){
        int RGB = 256;
        return new Color(ThreadLocalRandom.current().nextInt(RGB), ThreadLocalRandom.current().nextInt(RGB),
                ThreadLocalRandom.current().nextInt(RGB));
    }

    private XYLineAndShapeRenderer getDataRenderer(){
        return new XYLineAndShapeRenderer();
    }

    private XYErrorRenderer getErrorsRenderer(){
        XYErrorRenderer renderer = new XYErrorRenderer();
        renderer.setDrawYError(true);
        renderer.setDrawXError(true);
        renderer.setDrawSeriesLineAsPath(true);
        return renderer;
    }

    private XYIntervalSeriesCollection prepareCollection(){
        XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();
        for (XYIntervalSeries serie : series) dataset.addSeries(serie);
        return dataset;
    }

    public void createSeries(String name){
        series.add(new XYIntervalSeries(name));
    }

    public void addToDataSeries(int seriesIndx, double x, double lowX, double highX,
                                double y, double lowY, double highY){
        if (seriesIndx < series.size()){
            series.get(seriesIndx).add(x, lowX, highX, y, lowY, highY);
        }
    }
}
