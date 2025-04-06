package pjatk.psm.moon_trajectory_05;

import org.jfree.data.xy.XYSeries;

public class MidpointMoonSimulation {
    static final double G = 6.6743e-11;

    //mass [kg]
    static final double M_S = 1.989e30;
    static final double M_E = 5.972e24;
    static final double M_M = 7.347e22;

    //Distances
    static final double R_es = 1.5e11;
    static final double R_em = 3.844e8;

    static final double MOON_TRAJECTORY_SCALE = 25.0;

    double dt = 3600;                       // 1 hour in sec
    double totalTime = 365.25 * 24 * dt;  // 1 year in sec

    double xE, yE;
    double vxE, vyE;

    double xM, yM;
    double vxM, vyM;

    private final XYSeries earthSeries;
    private final XYSeries moonSeries;
    private final XYSeries sunSeries;

    public MidpointMoonSimulation() {
        earthSeries = new XYSeries("Earth Orbit");
        moonSeries  = new XYSeries("Moon Trajectory");
        sunSeries   = new XYSeries("Sun");

        sunSeries.add(0, 0);

        xE = R_es;
        yE = 0;
        vxE = 0;
        vyE = Math.sqrt(G * M_S / R_es);

        xM = R_em;
        yM = 0;
        vxM = 0;
        vyM = Math.sqrt(G * (M_E + M_M) / R_em);
    }

    public void run() {
        int steps = (int) (totalTime / dt);

        for (int i = 0; i < steps; i++) {
            earthSeries.add(xE, yE);
            moonSeries.add(xE + xM * MOON_TRAJECTORY_SCALE,
                    yE + yM * MOON_TRAJECTORY_SCALE);

            double rE = Math.sqrt(xE * xE + yE * yE);
            double axE = -G * M_S * xE / (rE * rE * rE);
            double ayE = -G * M_S * yE / (rE * rE * rE);

            double xE_mid  = xE  + vxE * dt / 2;
            double yE_mid  = yE  + vyE * dt / 2;
            double vxE_mid = vxE + axE * dt / 2;
            double vyE_mid = vyE + ayE * dt / 2;

            double rE_mid = Math.sqrt(xE_mid * xE_mid + yE_mid * yE_mid);
            double axE_mid = -G * M_S * xE_mid / (rE_mid * rE_mid * rE_mid);
            double ayE_mid = -G * M_S * yE_mid / (rE_mid * rE_mid * rE_mid);

            xE  += vxE_mid * dt;
            yE  += vyE_mid * dt;
            vxE += axE_mid * dt;
            vyE += ayE_mid * dt;

            double rM = Math.sqrt(xM * xM + yM * yM);
            double axM = -G * (M_E + M_M) * xM / (rM * rM * rM);
            double ayM = -G * (M_E + M_M) * yM / (rM * rM * rM);

            double xM_mid  = xM  + vxM * dt / 2;
            double yM_mid  = yM  + vyM * dt / 2;
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
