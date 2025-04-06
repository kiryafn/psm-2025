package pjatk.psm.pendulum_03.simulations;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSimulation implements Simulation {
    protected static final double G = 9.81;

    protected double mass;
    protected double length;
    protected double angle;
    protected double angularVelocity;

    protected double timeStep;
    protected double totalTime;

    protected List<Double[]> results;

    public BaseSimulation(double mass, double length, double initialAngle, double initialOmega, double timeStep, double totalTime) {
        this.mass = mass;
        this.length = length;
        this.angle = Math.toRadians(initialAngle);
        this.angularVelocity = initialOmega;
        this.timeStep = timeStep;
        this.totalTime = totalTime;
        this.results = new ArrayList<>();
    }

    @Override
    public double calculatePotentialEnergy(double angle) {
        return mass * Math.abs(G) * length * (1 - Math.cos(angle));
    }

    @Override
    public double calculateKineticEnergy(double angularVelocity) {
        return 0.5 * mass * length * length * angularVelocity * angularVelocity;
    }

    @Override
    public double calculateTotalEnergy(double potentialEnergy, double kineticEnergy) {
        return potentialEnergy + kineticEnergy;
    }

    @Override
    public void recordResult(double time, double angle, double omega) {
        double potentialEnergy = calculatePotentialEnergy(angle);
        double kineticEnergy = calculateKineticEnergy(omega);
        double totalEnergy = calculateTotalEnergy(potentialEnergy, kineticEnergy);

        results.add(new Double[]{time, potentialEnergy, kineticEnergy, totalEnergy, angle, omega});
    }

    @Override
    public List<Double[]> getResults() {
        return results;
    }
}