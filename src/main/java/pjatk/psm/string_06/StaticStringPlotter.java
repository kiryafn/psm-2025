package pjatk.psm.string_06;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class StaticStringPlotter extends JFrame {
    public StaticStringPlotter() {
        super("String: Shape & Energy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);

        int N = 50;
        double L = Math.PI;
        double dt = 0.01;
        double tMax = 5.0;
        double[] shapeTimes = {0.0, 0.05, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5};

        StringSimulation sim = new StringSimulation(N, L, dt);

        XYSeriesCollection shapeDataset = new XYSeriesCollection();
        XYSeriesCollection energyDataset = new XYSeriesCollection();
        XYSeries ekSeries = new XYSeries("Kinetic (Ek)");
        XYSeries epSeries = new XYSeries("Potential (Ep)");
        XYSeries etSeries = new XYSeries("Total (Et)");
        energyDataset.addSeries(ekSeries);
        energyDataset.addSeries(epSeries);
        energyDataset.addSeries(etSeries);

        for (double st : shapeTimes) {
            shapeDataset.addSeries(new XYSeries("t = " + st));
        }
        Set<Integer> dashed = new HashSet<>();
        dashed.add(0);
        recordShape(shapeDataset.getSeries(0), sim);

        double t = 0;
        int nextIdx = 1;
        int steps = (int)Math.round(tMax / dt);
        for (int k = 1; k <= steps; k++) {
            sim.step();
            t += dt;
            ekSeries.add(t, sim.kineticEnergy());
            epSeries.add(t, sim.potentialEnergy());
            etSeries.add(t, sim.totalEnergy());
            if (nextIdx < shapeTimes.length && Math.abs(t - shapeTimes[nextIdx]) < dt/2) {
                recordShape(shapeDataset.getSeries(nextIdx), sim);
                nextIdx++;
            }
        }

        JFreeChart shapeChart = ChartFactory.createXYLineChart(
                "String Shape throughout time", "x", "y",
                shapeDataset, PlotOrientation.VERTICAL, true, false, false);
        styleShapeChart(shapeChart.getXYPlot(), dashed, shapeTimes.length);

        JFreeChart energyChart = ChartFactory.createXYLineChart(
                "Energy Evolution of the String", "t", "E",
                energyDataset, PlotOrientation.VERTICAL, true, false, false);
        styleEnergyChart(energyChart.getXYPlot());

        ChartPanel p1 = new ChartPanel(shapeChart);
        ChartPanel p2 = new ChartPanel(energyChart);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p1, p2);
        split.setDividerLocation(300);
        getContentPane().add(split);
    }

    private void recordShape(XYSeries s, StringSimulation sim) {
        double[] y = sim.getY();
        double dx = sim.getDx();
        for (int i = 0; i < y.length; i++) {
            s.add(i * dx, y[i]);
        }
    }

    private void styleShapeChart(XYPlot plot, Set<Integer> dashed, int count) {
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer(true, false);
        for (int i = 0; i < count; i++) {
            if (dashed.contains(i)) {
                float[] dash = {6f, 6f};
                r.setSeriesStroke(i, new BasicStroke(2f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10f, dash, 0f));
                r.setSeriesPaint(i, Color.BLACK);
            } else {
                float hue = 0.75f - 0.6f * (i - 1) / (count - 2);
                r.setSeriesPaint(i, Color.getHSBColor(hue, 1f, 0.8f));
                r.setSeriesStroke(i, new BasicStroke(1.5f));
            }
        }
        plot.setRenderer(r);
    }

    private void styleEnergyChart(XYPlot plot) {
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer(true, false);
        r.setSeriesPaint(0, Color.RED);
        r.setSeriesPaint(1, Color.BLUE);
        r.setSeriesPaint(2, Color.GREEN.darker());
        r.setSeriesStroke(0, new BasicStroke(2f));
        r.setSeriesStroke(1, new BasicStroke(2f));
        r.setSeriesStroke(2, new BasicStroke(2f));
        plot.setRenderer(r);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StaticStringPlotter f = new StaticStringPlotter();
            f.setVisible(true);
        });
    }
}