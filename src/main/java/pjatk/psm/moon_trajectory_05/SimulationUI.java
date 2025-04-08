package pjatk.psm.moon_trajectory_05;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class SimulationUI {

    private XYSeriesCollection dataset;
    private ChartPanel chartPanel;
    private JTextField timeField;
    private JFrame frame;

    public SimulationUI(org.jfree.data.xy.XYSeries earthSeries,
                        org.jfree.data.xy.XYSeries moonSeries,
                        org.jfree.data.xy.XYSeries sunSeries) {
        dataset = new XYSeriesCollection();
        dataset.addSeries(earthSeries);
        dataset.addSeries(moonSeries);
        dataset.addSeries(sunSeries);
    }

    public void displayChart() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Moon Orbit Simulation",
                "X (m)",
                "Y (m)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        customizeChart(chart);
        chartPanel = new ChartPanel(chart);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Simulation Time (years):"));
        timeField = new JTextField(10);
        timeField.setText("1");
        controlPanel.add(timeField);

        JButton simulateButton = new JButton("Simulate");
        controlPanel.add(simulateButton);

        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateAndUpdateChart();
            }
        });

        frame = new JFrame("Moon Orbit Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void simulateAndUpdateChart() {
        double years;
        try {
            years = Double.parseDouble(timeField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid simulation time format.");
            return;
        }
        double simTimeSec = years * 365.25 * 24 * 3600;
        MidpointMoonSimulation simulation = new MidpointMoonSimulation(simTimeSec);
        simulation.run();

        dataset.removeAllSeries();
        dataset.addSeries(simulation.getEarthSeries());
        dataset.addSeries(simulation.getMoonSeries());
        dataset.addSeries(simulation.getSunSeries());

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Moon Orbit Simulation",
                "X (m)",
                "Y (m)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        customizeChart(chart);
        chartPanel.setChart(chart);
    }


    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(new Color(4, 0, 18));
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, new Color(32, 207, 35));
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 2, 2));

        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesPaint(1, Color.LIGHT_GRAY);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-2, -2, 2, 2));

        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesPaint(2, Color.ORANGE);
        renderer.setSeriesShape(2, new Ellipse2D.Double(-4, -4, 8, 8));

        plot.setRenderer(renderer);

        plot.getDomainAxis().setRange(-2e11, 2e11);
        plot.getRangeAxis().setRange(-2e11, 2e11);
    }
}
