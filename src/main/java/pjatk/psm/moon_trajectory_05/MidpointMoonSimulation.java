package pjatk.psm.moon_trajectory_05;

import org.jfree.data.xy.XYSeries;

public class MidpointMoonSimulation {

    static final double G = 6.6743e-11;
    static final double M_S = 1.989e30;
    static final double M_E = 5.972e24;
    static final double M_M = 7.347e22;

    static final double R_es = 1.5e11;
    static final double R_em = 3.844e8;

    static final double MOON_TRAJECTORY_SCALE = 25;

    double dt = 3600;
    double totalTime;

    double xE, yE;
    double vxE, vyE;

    double xM, yM;
    double vxM, vyM;

    private final XYSeries earthSeries;
    private final XYSeries moonSeries;
    private final XYSeries sunSeries;

    public MidpointMoonSimulation() {
        this(365.25 * 24 * 3600);
    }

    public MidpointMoonSimulation(double totalTime) {
        this.totalTime = totalTime;
        earthSeries = new XYSeries("Earth Orbit");
        moonSeries  = new XYSeries("Moon Trajectory");
        sunSeries   = new XYSeries("Sun");

        sunSeries.add(0, 0);

        xE = 0;
        yE = R_es;
        vxE = Math.sqrt(G * M_S / R_es);
        vyE = 0;

        xM = 0;
        yM = R_em;
        vxM = Math.sqrt(G * (M_E + M_M) / R_em);
        vyM = 0;
    }

    public void run() {
        int steps = (int) (totalTime / dt);
        for (int i = 0; i < steps; i++) {
            earthSeries.add(xE, yE);
            moonSeries.add(xE + xM * MOON_TRAJECTORY_SCALE,
                           yE + yM * MOON_TRAJECTORY_SCALE);

            //Earth around Sun
            double rE = Math.sqrt(xE * xE + yE * yE);
            double axE = -G * M_S * xE / (Math.pow(rE, 3));
            double ayE = -G * M_S * yE / (Math.pow(rE, 3));

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

            //Moon around Earth
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
