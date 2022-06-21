package simulation_5.visualization;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    ResultsTable resultsTable;
    Visualizer visualizer;
    private final String root = "src/main/resources/results/";


    public App(){

        resultsTable = new ResultsTable();
        visualizer = new Visualizer();
    }

    public void setUp(String fileName, int startRow, int rowCount, int colCount){

        setLayout(null);
        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        visualizer.prepareDataForPlot(root + fileName, startRow, rowCount, colCount);

        ChartPanel chartPanel = new ChartPanel(visualizer.getPlot());
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.WHITE);

        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.add(chartPanel, BorderLayout.CENTER);

        //addComp(mainPanel, chartPanel, 0, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        JPanel rightPanel = new JPanel();

        /*
        resultsTable.fillInTable(fileName, startRow, rowCount);
        JTable table = new JTable(resultsTable.tableModel);

        rightPanel.add(table);

        addComp(mainPanel, chartPanel, 0, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);
        addComp(mainPanel, rightPanel, 1, 0, 1,1, GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH);


         */

    }

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch){

        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = xPos;
        gridConstraints.gridy = yPos;
        gridConstraints.gridwidth = compWidth;
        gridConstraints.gridheight = compHeight;
        gridConstraints.weightx = 100;
        gridConstraints.weighty = 100;
        gridConstraints.insets = new Insets(5, 5, 5, 5);
        gridConstraints.anchor = place;
        gridConstraints.fill = stretch;

        thePanel.add(comp, gridConstraints);
    }

}
