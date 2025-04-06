package pjatk.psm.rolling_object_04.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GraphPanel extends JPanel {
    private ChartPanel motionChartPanel;
    private ChartPanel energyChartPanel;

    private final XYSeriesCollection motionTrajectoriesDataset = new XYSeriesCollection();
    private final XYSeriesCollection energiesDataset = new XYSeriesCollection();

    public GraphPanel() {
        setLayout(new GridLayout(1, 2));

        initMotionChartPanel();
        initEnergyChartPanel();

        add(motionChartPanel);
        add(energyChartPanel);
    }

    public void updateMotionDataset(XYSeriesCollection source) {
        motionTrajectoriesDataset.removeAllSeries();
        for (int i = 0; i < source.getSeriesCount(); i++) {
            motionTrajectoriesDataset.addSeries(source.getSeries(i));
        }
    }

    public void updateEnergiesDataset(XYSeriesCollection source) {
        energiesDataset.removeAllSeries();
        for (int i = 0; i < source.getSeriesCount(); i++) {
            energiesDataset.addSeries(source.getSeries(i));
        }
    }

    private void initMotionChartPanel(){
        JFreeChart trajectoriesChart = ChartFactory.createXYLineChart(
                "Rolling Object Motion Trajectories",
                "x",
                "y",
                motionTrajectoriesDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        trajectoriesChart.getXYPlot().setRenderer(initMotionRenderer());
        motionChartPanel = new ChartPanel(trajectoriesChart);
    }

    private void initEnergyChartPanel(){
        JFreeChart energiesChart = ChartFactory.createXYLineChart(
                "Rolling Object Energies",
                "Time (s)",
                "Energies (J)",
                energiesDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        energiesChart.getXYPlot().setRenderer(initEnergyRenderer());
        energyChartPanel = new ChartPanel(energiesChart);
    }

    private XYLineAndShapeRenderer initMotionRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(0, new Color(255, 71, 71));

        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-1, -1, 5, 5));
        renderer.setSeriesPaint(1, new Color(67, 246, 7));


        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShape(2, new Ellipse2D.Double(-2, -2, 2, 2));
        renderer.setSeriesPaint(2, new Color(255, 217, 36));

        renderer.setSeriesLinesVisible(3, false);
        renderer.setSeriesShapesVisible(3, true);
        renderer.setSeriesShape(3, new Ellipse2D.Double(-2, -2, 2, 2));
        renderer.setSeriesPaint(3, new Color(7, 111, 246));

        return renderer;
    }

    private XYLineAndShapeRenderer initEnergyRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesPaint(0, new Color(7, 111, 246));

        renderer.setSeriesShapesVisible(0, false);

        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        renderer.setSeriesPaint(1, new Color(255, 128, 0));
        renderer.setSeriesShapesVisible(1, false);

        renderer.setSeriesStroke(2, new BasicStroke(3.0f));
        renderer.setSeriesPaint(2, new Color(67, 246, 7));
        renderer.setSeriesShapesVisible(2, false);
        return renderer;
    }
}