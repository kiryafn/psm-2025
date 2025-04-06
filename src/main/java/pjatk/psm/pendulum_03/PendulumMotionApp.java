package pjatk.psm.pendulum_03;

import org.jfree.data.xy.XYSeries;
import pjatk.psm.pendulum_03.simulations.MidpointSimulation;
import pjatk.psm.pendulum_03.simulations.RK4Simulation;
import pjatk.psm.pendulum_03.simulations.Simulation;
import pjatk.psm.pendulum_03.ui.GraphPanel;
import pjatk.psm.pendulum_03.ui.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PendulumMotionApp extends JFrame {
    private InputPanel inputPanel;
    private GraphPanel graphPanel;

    public PendulumMotionApp() {
        super("Pendulum Simulation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        inputPanel = new InputPanel();
        graphPanel = new GraphPanel();

        add(inputPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        // Обработка кнопки "Simulate"
        inputPanel.getSimulateButton().addActionListener(e -> simulate());
    }

    private void simulate() {
        try {
            double mass = inputPanel.getMass();
            double length = inputPanel.getLength();
            double initialAngle = inputPanel.getInitialAngle();
            double totalTime = inputPanel.getTotalTime();
            double timeStep = inputPanel.getTimeStep();
            String method = inputPanel.getSimulationMethod();

            Simulation simulation;
            if ("Midpoint".equals(method)) {
                simulation = new MidpointSimulation(mass, length, initialAngle, 0, timeStep, totalTime);
            } else {
                simulation = new RK4Simulation(mass, length, initialAngle, 0, timeStep, totalTime);
            }

            simulation.runSimulation();

            List<Double[]> results = simulation.getResults();
            XYSeries potentialSeries = new XYSeries("Potential Energy");
            XYSeries kineticSeries = new XYSeries("Kinetic Energy");
            XYSeries totalSeries = new XYSeries("Total Energy");
            XYSeries phaseSeries = new XYSeries("Phase Trajectory");

            for (Double[] result : results) {
                double time = result[0];
                double potential = result[1];
                double kinetic = result[2];
                double total = result[3];
                double angle = result[4];
                double omega = result[5];

                potentialSeries.add(time, potential);
                kineticSeries.add(time, kinetic);
                totalSeries.add(time, total);
                phaseSeries.add(angle, omega);
            }

            graphPanel.updateEnergyChart(potentialSeries, kineticSeries, totalSeries, method);
            graphPanel.updatePhaseChart(phaseSeries, method);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PendulumMotionApp app = new PendulumMotionApp();
            app.setVisible(true);
        });
    }
}