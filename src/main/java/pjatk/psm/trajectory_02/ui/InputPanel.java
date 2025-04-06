package pjatk.psm.trajectory_02.ui;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private final JTextField dtField = new JTextField("0.1", 10);
    private final JTextField mField = new JTextField("1.0", 10);
    private final JTextField kField = new JTextField("0.1", 10);
    private final JTextField sx0Field = new JTextField("0", 10);
    private final JTextField sy0Field = new JTextField("0", 10);
    private final JTextField vx0Field = new JTextField("10", 10);
    private final JTextField vy0Field = new JTextField("10", 10);
    private final JButton simulateButton = new JButton("Simulate");

    public InputPanel(String panelTitle) {
        setLayout(new GridLayout(9, 1));
        setBorder(BorderFactory.createTitledBorder(panelTitle));
        setPreferredSize(new Dimension(200, 0));

        // Добавление полей ввода
        add(new JLabel("Time Step (dt):"));
        add(dtField);
        add(new JLabel("Mass (m):"));
        add(mField);
        add(new JLabel("Resistance (k):"));
        add(kField);
        add(new JLabel("Initial X (sx0):"));
        add(sx0Field);
        add(new JLabel("Initial Y (sy0):"));
        add(sy0Field);
        add(new JLabel("Initial Speed X (vx0):"));
        add(vx0Field);
        add(new JLabel("Initial Speed Y (vy0):"));
        add(vy0Field);

        // Кнопка запуска симуляции
        add(simulateButton);
    }

    public void setOnSimulateListener(SimulateListener listener) {
        simulateButton.addActionListener(e -> {
            try {
                double dt = Double.parseDouble(dtField.getText());
                double m = Double.parseDouble(mField.getText());
                double k = Double.parseDouble(kField.getText());
                double sx0 = Double.parseDouble(sx0Field.getText());
                double sy0 = Double.parseDouble(sy0Field.getText());
                double vx0 = Double.parseDouble(vx0Field.getText());
                double vy0 = Double.parseDouble(vy0Field.getText());

                listener.onSimulate(dt, m, k, sx0, sy0, vx0, vy0);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}