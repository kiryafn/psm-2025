package pjatk.psm.task03.ui;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private JTextField tfMass, tfLength, tfAngle, tfTime, tfDt;
    private JComboBox<String> simulationMethod;
    private JButton simulateButton;

    public InputPanel() {
        setLayout(new FlowLayout());

        add(new JLabel("Mass (kg):"));
        tfMass = new JTextField("10", 5);
        add(tfMass);

        add(new JLabel("Length (m):"));
        tfLength = new JTextField("1", 5);
        add(tfLength);

        add(new JLabel("Initial Angle (°):"));
        tfAngle = new JTextField("45", 5);
        add(tfAngle);

        add(new JLabel("Total Time (s):"));
        tfTime = new JTextField("10", 5);
        add(tfTime);

        add(new JLabel("Time Step (s):"));
        tfDt = new JTextField("0.01", 5);
        add(tfDt);

        add(new JLabel("Method:"));
        simulationMethod = new JComboBox<>(new String[]{"Midpoint", "RK4"});
        add(simulationMethod);

        simulateButton = new JButton("Simulate");
        add(simulateButton);
    }

    // Методы для получения пользовательских значений
    public double getMass() {
        return Double.parseDouble(tfMass.getText());
    }

    public double getLength() {
        return Double.parseDouble(tfLength.getText());
    }

    public double getInitialAngle() {
        return Double.parseDouble(tfAngle.getText());
    }

    public double getTotalTime() {
        return Double.parseDouble(tfTime.getText());
    }

    public double getTimeStep() {
        return Double.parseDouble(tfDt.getText());
    }

    public String getSimulationMethod() {
        return (String) simulationMethod.getSelectedItem();
    }

    public JButton getSimulateButton() {
        return simulateButton;
    }
}