package pjatk.psm.task02.simulations;

import java.util.ArrayList;
import java.util.List;

public class MidpointSimulation {
    private double dt;
    private double m;
    private double k;
    private double gx;
    private double gy;

    public List<Double> xData = new ArrayList<>();
    public List<Double> yData = new ArrayList<>();

    public MidpointSimulation(double dt, double m, double k, double gx, double gy) {
        this.dt = dt;
        this.m = m;
        this.k = k;
        this.gx = gx;
        this.gy = gy;
    }

    public MidpointSimulation(double dt, double m, double k) {
        this(dt, m, k, 0, -9.81);
    }

    public MidpointSimulation(double m, double k) {
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

            // 1️⃣ Вычисляем ускорение в начальной точке
            double ax1 = (m * gx - k * vx) / m;
            double ay1 = (m * gy - k * vy) / m;

            // 2️⃣ Вычисляем промежуточные скорости
            double vx_mid = vx + ax1 * (dt / 2);
            double vy_mid = vy + ay1 * (dt / 2);

            // 3️⃣ Вычисляем ускорение в середине шага
            double ax2 = (m * gx - k * vx_mid) / m;
            double ay2 = (m * gy - k * vy_mid) / m;

            // 4️⃣ Обновляем скорость с учетом середины шага
            vx += ax2 * dt;
            vy += ay2 * dt;

            // 5️⃣ Обновляем координаты с учетом середины шага
            sx += vx_mid * dt;
            sy += vy_mid * dt;

            if (sy < 0) {
                // Линейная интерполяция момента касания земли
                double tGround = -yData.get(yData.size() - 1) / vy;
                double xGround = xData.get(xData.size() - 1) + vx * tGround;
                double yGround = 0.0;

                xData.add(xGround);
                yData.add(yGround);
                break;
            }
        }
    }
}
