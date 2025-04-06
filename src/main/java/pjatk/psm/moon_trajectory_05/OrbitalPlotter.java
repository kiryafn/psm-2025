package pjatk.psm.moon_trajectory_05;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrbitalPlotter {

    public static void plot(List<MidpointSimulation.Vector> earthPath, List<MidpointSimulation.Vector> moonPath, List<MidpointSimulation.Vector> moonAroundEarthPath) {
        XYSeries earthSeries = new XYSeries("Earth");
        XYSeries moonSeries = new XYSeries("Moon");
        XYSeries moonAroundEarthSeries = new XYSeries("Moon (relative to Earth)");

        for (MidpointSimulation.Vector pos : earthPath) {
            earthSeries.add(pos.x, pos.y);
        }
        for (MidpointSimulation.Vector pos : moonPath) {
            moonSeries.add(pos.x, pos.y);
        }
        for (MidpointSimulation.Vector pos : moonAroundEarthPath) {
            moonAroundEarthSeries.add(pos.x, pos.y);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(earthSeries);
        dataset.addSeries(moonSeries);
        dataset.addSeries(moonAroundEarthSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Orbital Trajectories",
                "X Position (m)",
                "Y Position (m)",
                dataset
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesPaint(2, Color.GRAY);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesShapesVisible(2, false);

        plot.setRenderer(renderer);

        JFrame frame = new JFrame("Orbital Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new ChartPanel(chart), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}