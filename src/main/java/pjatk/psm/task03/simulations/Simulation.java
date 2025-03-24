package pjatk.psm.task03.simulations;

import java.util.List;

public interface Simulation {
    double calculatePotentialEnergy(double angle);

    double calculateKineticEnergy(double angularVelocity);

    double calculateTotalEnergy(double potentialEnergy, double kineticEnergy);

    void runSimulation();

    void recordResult(double time, double angle, double angularVelocity);

    List<Double[]> getResults();
}
