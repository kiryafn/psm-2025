package pjatk.psm.moon_trajectory_05;

public class MidpointSimulation {

    static final double G = 6.6743e-11; // гравитационная постоянная
    static final double Ms = 1.989e30; // масса Солнца
    static final double Me = 5.972e24; // масса Земли
    static final double Mm = 7.347e22; // масса Луны

    static final double R_Earth_Sun = 1.5e11; // начальное расстояние Земля-Солнце
    static final double R_Earth_Moon = 3.844e8; // начальное расстояние Земля-Луна
    static final double dt = 2000; // шаг по времени
    static final double totalTime = 3.154e7; // моделируем 1 год

    static class Vector {
        double x, y;

        Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Vector add(Vector v) {
            return new Vector(this.x + v.x, this.y + v.y);
        }

        Vector subtract(Vector v) {
            return new Vector(this.x - v.x, this.y - v.y);
        }

        Vector scale(double scalar) {
            return new Vector(this.x * scalar, this.y * scalar);
        }

        double magnitude() {
            return Math.sqrt(x * x + y * y);
        }

        Vector normalize() {
            double mag = magnitude();
            return new Vector(x / mag, y / mag);
        }
    }

    public static void main(String[] args) {
        int steps = (int) (totalTime / dt);

        Vector posSun = new Vector(0, 0);
        Vector posEarth = new Vector(R_Earth_Sun, 0);
        Vector velEarth = new Vector(0, 29780); // скорость орбиты Земли (м/с)

        Vector posMoon = posEarth.add(new Vector(0, R_Earth_Moon));
        Vector velMoon = velEarth.add(new Vector(1022, 0)); // орбитальная скорость Луны относительно Земли

        for (int i = 0; i < steps; i++) {
            // Гравитация Солнце-Земля
            Vector rSE = posSun.subtract(posEarth);
            double distSE = rSE.magnitude();
            Vector accEarth = rSE.scale(G * Ms / Math.pow(distSE, 3));

            // Обновим Землю (Midpoint)
            Vector velEarthHalf = velEarth.add(accEarth.scale(dt / 2));
            Vector posEarthHalf = posEarth.add(velEarth.scale(dt / 2));
            Vector rSEHalf = posSun.subtract(posEarthHalf);
            Vector accEarthHalf = rSEHalf.scale(G * Ms / Math.pow(rSEHalf.magnitude(), 3));
            velEarth = velEarth.add(accEarthHalf.scale(dt));
            posEarth = posEarth.add(velEarthHalf.scale(dt));

            // Гравитация Земля-Луна и Солнце-Луна
            Vector rEM = posEarth.subtract(posMoon);
            Vector rSM = posSun.subtract(posMoon);
            Vector accMoon = rEM.scale(G * Me / Math.pow(rEM.magnitude(), 3))
                    .add(rSM.scale(G * Ms / Math.pow(rSM.magnitude(), 3)));

            // Midpoint для Луны
            Vector velMoonHalf = velMoon.add(accMoon.scale(dt / 2));
            Vector posMoonHalf = posMoon.add(velMoon.scale(dt / 2));

            Vector rEMHalf = posEarth.subtract(posMoonHalf);
            Vector rSMHalf = posSun.subtract(posMoonHalf);
            Vector accMoonHalf = rEMHalf.scale(G * Me / Math.pow(rEMHalf.magnitude(), 3))
                    .add(rSMHalf.scale(G * Ms / Math.pow(rSMHalf.magnitude(), 3)));

            velMoon = velMoon.add(accMoonHalf.scale(dt));
            posMoon = posMoon.add(velMoonHalf.scale(dt));

            // Можно сохранить точки траектории для построения графика
            // Например: сохранять в ArrayList<Vector> и потом отрисовать через JFreeChart
        }

        System.out.println("Симуляция завершена.");
    }
}
