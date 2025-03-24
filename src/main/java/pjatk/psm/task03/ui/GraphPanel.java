package pjatk.psm.task03.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {
    private ChartPanel energyChartPanel;
    private ChartPanel phaseChartPanel;

    public GraphPanel() {
        setLayout(new GridLayout(1, 2));

        // Создаем начальные холостые графики
        energyChartPanel = new ChartPanel(null);
        phaseChartPanel = new ChartPanel(null);

        add(energyChartPanel);
        add(phaseChartPanel);
    }

    // Метод для обновления графика энергоизменений
    public void updateEnergyChart(XYSeries potential, XYSeries kinetic, XYSeries total, String method) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(potential);
        dataset.addSeries(kinetic);
        dataset.addSeries(total);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Energy vs Time (" + method + ")",
                "Time (s)",
                "Energy (J)",
                dataset
        );
        energyChartPanel.setChart(chart);
    }

    // Метод для обновления графика фазовой траектории с точной настройкой точки в виде круга
    public void updatePhaseChart(XYSeries phase, String method) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(phase);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Phase Trajectory (" + method + ")",
                "Angle (rad)",
                "Angular Velocity (rad/s)",
                dataset
        );

        // Доступ к построению графика
        XYPlot plot = chart.getXYPlot();

        // Настраиваем кастомный рендерер
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Отключаем соединение точек
        renderer.setSeriesLinesVisible(0, false);

        // Задаем форму: маленькие круги вместо квадратов
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-2, -2, 4, 4)); // Круг размером 4x4 пикселя
        renderer.setSeriesShapesVisible(0, true);

        // Применяем рендерер к графику
        plot.setRenderer(renderer);

        // Обновляем диаграмму в панели
        phaseChartPanel.setChart(chart);
    }

    // Очистить графики
    public void clearCharts() {
        energyChartPanel.setChart(null);
        phaseChartPanel.setChart(null);
    }
}