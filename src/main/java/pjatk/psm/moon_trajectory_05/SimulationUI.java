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

    // Конструктор принимает серии, полученные из симуляции
    public SimulationUI(org.jfree.data.xy.XYSeries earthSeries,
                        org.jfree.data.xy.XYSeries moonSeries,
                        org.jfree.data.xy.XYSeries sunSeries) {
        dataset = new XYSeriesCollection();
        dataset.addSeries(earthSeries);  // Серия 0 – орбита Земли
        dataset.addSeries(moonSeries);     // Серия 1 – траектория Луны
        dataset.addSeries(sunSeries);      // Серия 2 – Солнце
    }

    /**
     * Создает окно с графиком и панелью управления.
     */
    public void displayChart() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Moon Orbit Simulation",
                "X (m)",
                "Y (m)",
                dataset,
                PlotOrientation.VERTICAL,
                true,    // легенда
                true,    // tooltips
                false    // urls
        );
        customizeChart(chart);
        chartPanel = new ChartPanel(chart);

        // Панель управления: текстовое поле для длительности симуляции (в годах) и кнопка "Simulate"
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Simulation Time (years):"));
        timeField = new JTextField(10);
        // По умолчанию 1 год
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

    /**
     * Создает новую симуляцию с заданной длительностью (в годах), запускает расчет и обновляет график.
     */
    private void simulateAndUpdateChart() {
        double years;
        try {
            years = Double.parseDouble(timeField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid simulation time format.");
            return;
        }
        // Переводим длительность из лет в секунды
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

    /**
     * Кастомизация графика: задаются цвета, стиль линий и диапазоны осей.
     */
    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();

        plot.setBackgroundPaint(new Color(10, 0, 48));
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Серия 0: орбита Земли – зеленые точки
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, new Color(0, 120, 1));
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 2, 2));

        // Серия 1: траектория Луны – серые точки
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesPaint(1, Color.LIGHT_GRAY);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-2, -2, 2, 2));

        // Серия 2: Солнце – оранжевая точка
        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesPaint(2, Color.ORANGE);
        renderer.setSeriesShape(2, new Ellipse2D.Double(-4, -4, 8, 8));

        plot.setRenderer(renderer);

        plot.getDomainAxis().setRange(-2e11, 2e11);
        plot.getRangeAxis().setRange(-2e11, 2e11);
    }
}
