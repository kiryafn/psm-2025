package pjatk.psm.moon_trajectory_05;

import org.jfree.data.xy.XYSeries;

public class MidpointMoonSimulation {

    // Физические константы
    static final double G = 6.6743e-11;      // Гравитационная постоянная [м^3/(кг·с^2)]
    static final double M_S = 1.989e30;        // Масса Солнца [кг]
    static final double M_E = 5.972e24;        // Масса Земли [кг]
    static final double M_M = 7.347e22;        // Масса Луны [кг]

    // Радиусы орбит
    static final double R_es = 1.5e11;         // Расстояние Земля-Солнце [м]
    static final double R_em = 3.844e8;        // Расстояние Земля-Луна [м]

    // Коэффициент масштабирования траектории Луны для визуализации (чтобы "волна" была видна)
    static final double MOON_TRAJECTORY_SCALE = 25.0;

    // Параметры симуляции
    double dt = 21600;                      // шаг по времени – 6 часов (в секундах)
    double totalTime = 365.25 * 24 * 3600;    // время симуляции – один год (в секундах)

    // Состояние Земли (абсолютные координаты относительно Солнца)
    double xE, yE;
    double vxE, vyE;

    // Состояние Луны (координаты относительно Земли)
    double xM, yM;
    double vxM, vyM;

    // Серии для графика
    private final XYSeries earthSeries;
    private final XYSeries moonSeries;
    private final XYSeries sunSeries;

    public MidpointMoonSimulation() {
        // Инициализируем серии для отрисовки
        earthSeries = new XYSeries("Earth Orbit");
        moonSeries = new XYSeries("Moon Trajectory");
        sunSeries = new XYSeries("Sun");

        // Солнце – точка в центре (0,0)
        sunSeries.add(0, 0);

        // Начальные условия для Земли:
        // Земля расположена в точке (R_es, 0) и движется со скоростью по оси Y для круговой орбиты вокруг Солнца
        xE = R_es;
        yE = 0;
        vxE = 0;
        vyE = Math.sqrt(G * M_S / R_es);

        // Начальные условия для Луны относительно Земли:
        // Луна расположена в точке (R_em, 0)
        // Для круговой орбиты относительно Земли скорость задается как:
        // v = sqrt( G*(M_E + M_M)/R_em )
        xM = R_em;
        yM = 0;
        vxM = 0;
        vyM = Math.sqrt(G * (M_E + M_M) / R_em);
    }

    /**
     * Основной цикл симуляции, реализованный методом середины (Midpoint).
     */
    public void run() {
        int steps = (int) (totalTime / dt);
        for (int i = 0; i < steps; i++) {
            // Записываем текущие координаты Земли и абсолютное положение Луны (с масштабированием)
            earthSeries.add(xE, yE);
            moonSeries.add(xE + xM * MOON_TRAJECTORY_SCALE, yE + yM * MOON_TRAJECTORY_SCALE);

            // --- Интегрирование движения Земли вокруг Солнца ---
            double rE = Math.sqrt(xE * xE + yE * yE);
            double axE = -G * M_S * xE / (rE * rE * rE);
            double ayE = -G * M_S * yE / (rE * rE * rE);

            // Шаг метода середины для Земли
            double xE_mid = xE + vxE * dt / 2;
            double yE_mid = yE + vyE * dt / 2;
            double vxE_mid = vxE + axE * dt / 2;
            double vyE_mid = vyE + ayE * dt / 2;

            double rE_mid = Math.sqrt(xE_mid * xE_mid + yE_mid * yE_mid);
            double axE_mid = -G * M_S * xE_mid / (rE_mid * rE_mid * rE_mid);
            double ayE_mid = -G * M_S * yE_mid / (rE_mid * rE_mid * rE_mid);

            // Обновляем положение и скорость Земли
            xE += vxE_mid * dt;
            yE += vyE_mid * dt;
            vxE += axE_mid * dt;
            vyE += ayE_mid * dt;

            // --- Интегрирование движения Луны вокруг Земли ---
            double rM = Math.sqrt(xM * xM + yM * yM);
            // Используем суммарную массу для расчёта относительного ускорения Луны
            double axM = -G * (M_E + M_M) * xM / (rM * rM * rM);
            double ayM = -G * (M_E + M_M) * yM / (rM * rM * rM);

            // Шаг метода середины для Луны
            double xM_mid = xM + vxM * dt / 2;
            double yM_mid = yM + vyM * dt / 2;
            double vxM_mid = vxM + axM * dt / 2;
            double vyM_mid = vyM + ayM * dt / 2;

            double rM_mid = Math.sqrt(xM_mid * xM_mid + yM_mid * yM_mid);
            double axM_mid = -G * (M_E + M_M) * xM_mid / (rM_mid * rM_mid * rM_mid);
            double ayM_mid = -G * (M_E + M_M) * yM_mid / (rM_mid * rM_mid * rM_mid);

            // Обновляем положение и скорость Луны относительно Земли
            xM += vxM_mid * dt;
            yM += vyM_mid * dt;
            vxM += axM_mid * dt;
            vyM += ayM_mid * dt;
        }
    }

    // Геттеры для серий (для построения графика)
    public XYSeries getEarthSeries() {
        return earthSeries;
    }
    public XYSeries getMoonSeries() {
        return moonSeries;
    }
    public XYSeries getSunSeries() {
        return sunSeries;
    }

    /**
     * Точка входа
     */
    public static void main(String[] args) {
        MidpointMoonSimulation simulation = new MidpointMoonSimulation();
        simulation.run();

        SimulationUI ui = new SimulationUI(
                simulation.getEarthSeries(),
                simulation.getMoonSeries(),
                simulation.getSunSeries()
        );
        ui.displayChart();
    }
}
