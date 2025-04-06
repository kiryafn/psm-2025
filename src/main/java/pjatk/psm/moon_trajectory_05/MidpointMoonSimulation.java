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
    static final double R_em = 3.844e8;         // Расстояние Земля-Луна [м]

    // Коэффициент масштабирования траектории Луны для визуализации
    static final double MOON_TRAJECTORY_SCALE = 25.0;

    // Параметры симуляции
    double dt = 3600;                       // 1 час (в секундах)
    double totalTime;    // по умолчанию 1 год (в секундах)

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

    // Конструктор по умолчанию – 1 год симуляции
    public MidpointMoonSimulation() {
        this(365.25 * 24 * 3600);
    }

    // Конструктор с заданной длительностью симуляции (в секундах)
    public MidpointMoonSimulation(double totalTime) {
        this.totalTime = totalTime;
        earthSeries = new XYSeries("Earth Orbit");
        moonSeries  = new XYSeries("Moon Trajectory");
        sunSeries   = new XYSeries("Sun");

        // Солнце в центре (0,0)
        sunSeries.add(0, 0);

        // Начальные условия для Земли:
        xE = R_es;
        yE = 0;
        vxE = 0;
        vyE = Math.sqrt(G * M_S / R_es);

        // Начальные условия для Луны относительно Земли:
        xM = R_em;
        yM = 0;
        vxM = 0;
        vyM = Math.sqrt(G * (M_E + M_M) / R_em);
    }

    /**
     * Выполняет численное моделирование движения Земли и Луны методом середины.
     */
    public void run() {
        int steps = (int) (totalTime / dt);
        for (int i = 0; i < steps; i++) {
            earthSeries.add(xE, yE);
            moonSeries.add(xE + xM * MOON_TRAJECTORY_SCALE,
                    yE + yM * MOON_TRAJECTORY_SCALE);

            // Интегрирование движения Земли вокруг Солнца
            double rE = Math.sqrt(xE * xE + yE * yE);
            double axE = -G * M_S * xE / (rE * rE * rE);
            double ayE = -G * M_S * yE / (rE * rE * rE);

            double xE_mid  = xE + vxE * dt / 2;
            double yE_mid  = yE + vyE * dt / 2;
            double vxE_mid = vxE + axE * dt / 2;
            double vyE_mid = vyE + ayE * dt / 2;

            double rE_mid = Math.sqrt(xE_mid * xE_mid + yE_mid * yE_mid);
            double axE_mid = -G * M_S * xE_mid / (rE_mid * rE_mid * rE_mid);
            double ayE_mid = -G * M_S * yE_mid / (rE_mid * rE_mid * rE_mid);

            xE  += vxE_mid * dt;
            yE  += vyE_mid * dt;
            vxE += axE_mid * dt;
            vyE += ayE_mid * dt;

            // Интегрирование движения Луны вокруг Земли
            double rM = Math.sqrt(xM * xM + yM * yM);
            double axM = -G * (M_E + M_M) * xM / (rM * rM * rM);
            double ayM = -G * (M_E + M_M) * yM / (rM * rM * rM);

            double xM_mid  = xM + vxM * dt / 2;
            double yM_mid  = yM + vyM * dt / 2;
            double vxM_mid = vxM + axM * dt / 2;
            double vyM_mid = vyM + ayM * dt / 2;

            double rM_mid = Math.sqrt(xM_mid * xM_mid + yM_mid * yM_mid);
            double axM_mid = -G * (M_E + M_M) * xM_mid / (rM_mid * rM_mid * rM_mid);
            double ayM_mid = -G * (M_E + M_M) * yM_mid / (rM_mid * rM_mid * rM_mid);

            xM  += vxM_mid * dt;
            yM  += vyM_mid * dt;
            vxM += axM_mid * dt;
            vyM += ayM_mid * dt;
        }
    }

    public XYSeries getEarthSeries() { return earthSeries; }
    public XYSeries getMoonSeries()  { return moonSeries; }
    public XYSeries getSunSeries()   { return sunSeries; }

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
