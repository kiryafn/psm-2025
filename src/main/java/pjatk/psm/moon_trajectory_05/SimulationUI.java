package pjatk.psm.moon_trajectory_05;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

class SimulationUI {

    private final org.jfree.data.xy.XYSeries earthSeries;
    private final org.jfree.data.xy.XYSeries moonSeries;
    private final org.jfree.data.xy.XYSeries sunSeries;

    public SimulationUI(org.jfree.data.xy.XYSeries earthSeries, org.jfree.data.xy.XYSeries moonSeries, org.jfree.data.xy.XYSeries sunSeries) {
        this.earthSeries = earthSeries;
        this.moonSeries  = moonSeries;
        this.sunSeries   = sunSeries;
    }

    /**
     * Создание и отображение окна с графиком.
     */
    public void displayChart() {
        // Собираем все серии в один набор данных
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(earthSeries);  // Серия 0 – орбита Земли
        dataset.addSeries(moonSeries);     // Серия 1 – траектория Луны
        dataset.addSeries(sunSeries);      // Серия 2 – Солнце

        // Создаём базовый график
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Moon Orbit Simulation",
                "X (m)",
                "Y (m)",
                dataset,
                PlotOrientation.VERTICAL,
                true,    // legend
                true,    // tooltips
                false    // urls
        );

        // Применяем кастомизацию для отображения ТОЛЬКО точек
        customizeChart(chart);

        // Размещаем график в окне Swing
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame("Moon Orbit Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Настройка отображения графика: отключение линий и включение отрисовки маркеров (точек).
     */
    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();

        // Фон и сетка
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Рендерер, который отрисовывает только точки
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Серия 0: Орбита Земли – красные точки
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 4, 4));

        // Серия 1: Траектория Луны – синие точки
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesShape(1, new Ellipse2D.Double(-2, -2, 4, 4));

        // Серия 2: Солнце – одна жёлтая точка
        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesPaint(2, Color.ORANGE);
        renderer.setSeriesShape(2, new Ellipse2D.Double(-4, -4, 8, 8));

        plot.setRenderer(renderer);

        // Фиксированный диапазон осей для оптимального масштабирования
        plot.getDomainAxis().setRange(-2e11, 2e11);
        plot.getRangeAxis().setRange(-2e11, 2e11);
    }
}
