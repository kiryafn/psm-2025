package pjatk.psm.trajectory_02.simulations;

import java.util.ArrayList;
import java.util.List;

public class EulerSimulation {
    private double dt;
    private double m;
    private double k;
    private double gx;
    private double gy;

    public List<Double> xData = new ArrayList<>();
    public List<Double> yData = new ArrayList<>();

    public EulerSimulation(double dt, double m, double k, double gx, double gy) {
        this.dt = dt;
        this.m = m;
        this.k = k;
        this.gx = gx;
        this.gy = gy;
    }

    public EulerSimulation(double dt, double m, double k) {
        this(dt, m, k, 0, -9.81);
    }

    public EulerSimulation(double m, double k) {
        this(0.1, m, k, 0, -9.81);
    }

    public void simulate(double sx0, double sy0, double vx0, double vy0) {
        double sx = sx0, sy = sy0;
        double vx = vx0, vy = vy0;

        xData.clear();
        yData.clear();

        while (sy >= 0) {
            xData.add(sx);
            yData.add(sy);

            double ax = (m * gx - k * vx) / m;
            double ay = (m * gy - k * vy) / m;

            vx += ax * dt;
            vy += ay * dt;

            sx += vx * dt;
            sy += vy * dt;

            if (sy < 0) {
                double tGround = - (yData.get(yData.size() - 1) / vy);
                double xGround = xData.get(xData.size() - 1) + vx * tGround;
                double yGround = 0.0;

                xData.add(xGround);
                yData.add(yGround);

                break;
            }
        }
    }

}
