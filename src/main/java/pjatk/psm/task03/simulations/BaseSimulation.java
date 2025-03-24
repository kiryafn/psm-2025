package pjatk.psm.task03.simulations;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSimulation {
    protected static final double G = 9.81;

    protected double mass;
    protected double length;
    protected double angle;
    protected double angularVelocity;

    protected double timeStep;
    protected double totalTime;

    protected List<Double[]> results; // Сохраняет {время, Ep, Ek, Et, α, ω}

    public BaseSimulation(double mass, double length, double initialAngle, double initialOmega, double timeStep, double totalTime) {
        this.mass = mass;
        this.length = length;
        this.angle = Math.toRadians(initialAngle); // Преобразуем угол в радианы
        this.angularVelocity = initialOmega;
        this.timeStep = timeStep;
        this.totalTime = totalTime;
        this.results = new ArrayList<>();
    }

    // Метод для расчета потенциальной энергии
    protected double calculatePotentialEnergy(double angle) {
        return mass * G * length * (1 - Math.cos(angle));
    }

    // Метод для расчета кинетической энергии
    protected double calculateKineticEnergy(double angularVelocity) {
        return 0.5 * mass * length * length * angularVelocity * angularVelocity;
    }

    // Метод для расчета полной энергии
    protected double calculateTotalEnergy(double potentialEnergy, double kineticEnergy) {
        return potentialEnergy + kineticEnergy;
    }

    // Абстрактный метод — каждый подкласс должен реализовать логику симуляции
    public abstract void runSimulation();

    // Общая логика: добавление результатов в список
    protected void recordResult(double time, double angle, double omega) {
        double potentialEnergy = calculatePotentialEnergy(angle);
        double kineticEnergy = calculateKineticEnergy(omega);
        double totalEnergy = calculateTotalEnergy(potentialEnergy, kineticEnergy);

        // Сохраняем результат в виде массива {время, Ep, Ek, Et, α, ω}
        results.add(new Double[]{time, potentialEnergy, kineticEnergy, totalEnergy, angle, omega});
    }

    // Метод возвращает все результаты (для вывода или построения графиков)
    public List<Double[]> getResults() {
        return results;
    }
}