package pjatk.psm.task02.ui;

@FunctionalInterface
public interface SimulateListener {
    void onSimulate(double dt, double m, double k, double sx0, double sy0, double vx0, double vy0);
}