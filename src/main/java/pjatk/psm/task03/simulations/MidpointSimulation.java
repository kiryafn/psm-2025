package pjatk.psm.task03.simulations;

import pjatk.psm.task03.simulations.BaseSimulation;

public class MidpointSimulation extends BaseSimulation {

    public MidpointSimulation(double mass, double length, double initialAngle, double initialOmega, double timeStep, double totalTime) {
        super(mass, length, initialAngle, initialOmega, timeStep, totalTime);
    }

    @Override
    public void runSimulation() {
        int steps = (int) (totalTime / timeStep);
        double currentAngle = this.angle;
        double currentOmega = this.angularVelocity;

        for (int i = 0; i <= steps; i++) {
            double time = i * timeStep;

            // Сохраняем текущие значения
            recordResult(time, currentAngle, currentOmega);

            // Предиктор: рассчитываем наклоны
            double slopeAlpha1 = currentOmega;
            double slopeOmega1 = -(G / length) * Math.sin(currentAngle);

            double predictedAlpha = currentAngle + timeStep * slopeAlpha1;
            double predictedOmega = currentOmega + timeStep * slopeOmega1;

            // Корректор: рассчитываем новые наклоны
            double slopeAlpha2 = predictedOmega;
            double slopeOmega2 = -(G / length) * Math.sin(predictedAlpha);

            // Обновляем значения
            currentAngle += 0.5 * timeStep * (slopeAlpha1 + slopeAlpha2);
            currentOmega += 0.5 * timeStep * (slopeOmega1 + slopeOmega2);
        }
    }
}