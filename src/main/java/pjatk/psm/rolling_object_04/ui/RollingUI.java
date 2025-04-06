package pjatk.psm.rolling_object_04.ui;

import org.jfree.data.xy.XYSeriesCollection;
import pjatk.psm.rolling_object_04.simulation.RollingSimulation;

import javax.swing.*;
import java.awt.*;

public class RollingUI extends JFrame {
    InputPanel inputPanel;
    GraphPanel graphPanel;

    public void createAndShowGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1200, 800);

        inputPanel = new InputPanel();
        inputPanel.getSimulateButton().addActionListener(e -> runSimulation());

        graphPanel = new GraphPanel();

        add(inputPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void runSimulation() {
        try {
            double mass    = inputPanel.getMass();
            double radius  = inputPanel.getRadius();
            double angle   = inputPanel.getAngle();
            double height  = inputPanel.getLineHeight();
            double inertia = inputPanel.getInertia();
            double dt      = inputPanel.getTimeStep();

            XYSeriesCollection trajectoriesDataset = new XYSeriesCollection();
            XYSeriesCollection energiesDataset = new XYSeriesCollection();

            RollingSimulation rollingObject = new RollingSimulation(mass, height, radius, angle, inertia);

            rollingObject.simulate(dt);

            trajectoriesDataset.addSeries(rollingObject.coordinatesDatabase.getInclineLineCoordinates());
            trajectoriesDataset.addSeries(rollingObject.coordinatesDatabase.getBallProjectionCoordinates());
            trajectoriesDataset.addSeries(rollingObject.coordinatesDatabase.getBallCentreCoordinates());
            trajectoriesDataset.addSeries(rollingObject.coordinatesDatabase.getBallCoordinates());

            energiesDataset.addSeries(rollingObject.coordinatesDatabase.getEpCoordinates());
            energiesDataset.addSeries(rollingObject.coordinatesDatabase.getEkCoordinates());
            energiesDataset.addSeries(rollingObject.coordinatesDatabase.getEtCoordinates());

            graphPanel.updateMotionDataset(trajectoriesDataset);
            graphPanel.updateEnergiesDataset(energiesDataset);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numbers.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}