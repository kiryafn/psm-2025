package pjatk.psm.string_06;

public class StringSimulation {
    private final int N;
    private final double dx, dt;
    private double[] y, v, a;

    public StringSimulation(int N, double L, double dt) {
        this.N = N;
        this.dt = dt;
        this.dx = L / N;
        this.y = new double[N + 1];
        this.v = new double[N + 1];
        this.a = new double[N + 1];
        init();
    }

    private void init() {
        double A = 0.1;
        for (int i = 0; i <= N; i++) {
            y[i] = A * Math.sin(i * dx);
            v[i] = 0;
        }
        computeAcceleration(y, a);
    }

    private void computeAcceleration(double[] yy, double[] aa) {
        for (int i = 1; i < N; i++) {
            aa[i] = (yy[i - 1] - 2 * yy[i] + yy[i + 1]) / (dx * dx);
        }
        aa[0] = aa[N] = 0;
    }

    public void step() {
        computeAcceleration(y, a);

        double[] yMid = new double[N + 1];
        double[] vMid = new double[N + 1];
        for (int i = 0; i <= N; i++) {
            yMid[i] = y[i] + v[i] * dt / 2;
            vMid[i] = v[i] + a[i] * dt / 2;
        }

        double[] aMid = new double[N + 1];
        computeAcceleration(yMid, aMid);

        for (int i = 1; i < N; i++) {
            y[i] += vMid[i] * dt;
            v[i] += aMid[i] * dt;
        }

        y[0] = y[N] = 0;
        v[0] = v[N] = 0;
    }

    public double[] getY()  { return y; }
    public double   getDx() { return dx; }

    public double kineticEnergy() {
        double Ek = 0;
        for (int i = 0; i <= N; i++) {
            Ek += 0.5 * v[i] * v[i] * dx;
        }
        return Ek;
    }

    public double potentialEnergy() {
        double Ep = 0;
        for (int i = 0; i < N; i++) {
            double dy = y[i + 1] - y[i];
            Ep += 0.5 * (dy * dy) / dx;
        }
        return Ep;
    }

    public double totalEnergy() {
        return kineticEnergy() + potentialEnergy();
    }
}