package simulation_2.testing;

import simulation_2.algorithms.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;

public class PlotCylinderHeadMoves extends JFrame {

    // List of generated requests
    private List<Request> list;
    private int discSize;
    private int totalMoves;
    private int width;
    private int heightOffset;
    private int height;
    private final float strokeSize = 2.0f;

    public PlotCylinderHeadMoves(List<Request> list, int discSize, int totalMoves, int width, int height){
        this.list = list;
        this.discSize = discSize;
        this.totalMoves = totalMoves;
        this.width = width;
        this.height = height;
        this.heightOffset = list.get(0).getServiceTime();

        setSize(width, height);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private double scaleX(Request request){
        return (double) request.getPosition() / discSize * width;
    }

    private double scaleY(Request request){
        return (double) (request.getServiceTime() - heightOffset) / totalMoves * height;
    }

    private Path2D createPath(){

        Path2D path = new Path2D.Double();
        path.moveTo(scaleX(list.get(0)), scaleY(list.get(0)));
        for (Request r : list){
            path.lineTo(scaleX(r), scaleY(r));
        }
        return path;
    }

    @Override
    public void paint(Graphics graphics){

        Graphics2D g2D = (Graphics2D) graphics;
        g2D.setBackground(Color.WHITE);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                // This should smooth the edges
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setStroke(new BasicStroke(strokeSize));

        g2D.draw(createPath());
    }

}
