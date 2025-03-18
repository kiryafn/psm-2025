package pjatk.psm.task02.ui;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartPanel extends JPanel {
    private XYChart chart;

    public ChartPanel() {
        chart = new XYChartBuilder()
                .width(600)
                .height(600)
                .title("Trajectory Comparison")
                .xAxisTitle("X Position")
                .yAxisTitle("Y Position")
                .build();

        // Настройка стиля графика
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setMarkerSize(6);

        setLayout(new BorderLayout());
        add(new XChartPanel<>(chart), BorderLayout.CENTER);
    }

    public void updateChart(String seriesName, List<Double> xData, List<Double> yData, String color) {
        // Проверка на пустые данные
        if (xData == null || yData == null || xData.isEmpty() || yData.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Simulated data is empty. Please check your inputs!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Добавляем или обновляем серию данных
        if (chart.getSeriesMap().containsKey(seriesName)) {
            chart.updateXYSeries(seriesName, xData, yData, null);
        } else {
            var series = chart.addSeries(seriesName, xData, yData);

            // Применяем стиль на основе цвета
            switch (color.toLowerCase()) {
                case "blue":
                    series.setMarker(SeriesMarkers.CIRCLE);
                    series.setLineColor(Color.BLUE);
                    break;
                case "red":
                    series.setMarker(SeriesMarkers.SQUARE);
                    series.setLineColor(Color.RED);
                    break;
                default:
                    series.setMarker(SeriesMarkers.NONE);
                    series.setLineColor(Color.BLACK);
                    break;
            }
        }

        repaint();  // Перерисовываем панель
        revalidate();
    }
}