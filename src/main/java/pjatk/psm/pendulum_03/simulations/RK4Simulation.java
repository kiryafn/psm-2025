package pjatk.psm.pendulum_03.simulations;

public class RK4Simulation extends BaseSimulation {

    public RK4Simulation(double mass, double length, double initialAngle, double initialOmega, double timeStep, double totalTime) {
        super(mass, length, initialAngle, initialOmega, timeStep, totalTime);
    }

    @Override
    public void runSimulation() {
        int steps = (int) (totalTime / timeStep);
        double currentAngle = this.angle;
        double currentOmega = this.angularVelocity;

        for (int i = 0; i <= steps; i++) {
            double time = i * timeStep;

            recordResult(time, currentAngle, currentOmega);

            //Step 1
            double dOmega1 = -(G / length) * Math.sin(currentAngle);
            double dAlpha1 = currentOmega;

            //Step 2
            double dOmega2 = -(G / length) * Math.sin(currentAngle + 0.5 * timeStep * dAlpha1);
            double dAlpha2 = currentOmega + 0.5 * timeStep * dOmega1;

            //Step 3
            double dOmega3 = -(G / length) * Math.sin(currentAngle + 0.5 * timeStep * dAlpha2);
            double dAlpha3 = currentOmega + 0.5 * timeStep * dOmega2;

            //Step 4
            double dOmega4 = -(G / length) * Math.sin(currentAngle + timeStep * dAlpha3);
            double dAlpha4 = currentOmega + timeStep * dOmega3;

            //Update values
            currentAngle += (timeStep / 6.0) * (dAlpha1 + 2 * dAlpha2 + 2 * dAlpha3 + dAlpha4);
            currentOmega += (timeStep / 6.0) * (dOmega1 + 2 * dOmega2 + 2 * dOmega3 + dOmega4);
        }
    }
}