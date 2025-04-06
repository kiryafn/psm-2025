package pjatk.psm.rolling_object_04.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InputPanel extends JPanel {
    private JTextField massField;
    private JTextField radiusField;
    private JTextField angleField;
    private JTextField heightField;
    private JTextField inertiaField;
    private JTextField dtField;

    private JButton simulateButton;

    public InputPanel() {
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                "Input",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.DARK_GRAY));
        setBackground(Color.LIGHT_GRAY);

        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        massField = createInputField("10", inputFont);
        radiusField = createInputField("10", inputFont);
        angleField = createInputField("45", inputFont);
        heightField = createInputField("100", inputFont);
        inertiaField = createInputField("3.6", inputFont);
        dtField = createInputField("0.01", inputFont);

        simulateButton = new JButton("Simulate");
        simulateButton.setFont(new Font("Arial", Font.BOLD, 14));
        simulateButton.setBackground(new Color(70, 130, 180));
        simulateButton.setForeground(Color.BLACK);
        simulateButton.setFocusPainted(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addInputRow(gbc, 0, "Mass:", massField);
        addInputRow(gbc, 1, "Radius:", radiusField);
        addInputRow(gbc, 2, "Angle:", angleField);
        addInputRow(gbc, 3, "Height:", heightField);
        addInputRow(gbc, 4, "Time Step:", dtField);
        addInputRow(gbc, 5, "Inertia:", inertiaField);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(simulateButton, gbc);
    }

    private JTextField createInputField(String defaultText, Font font) {
        JTextField field = new JTextField(defaultText);
        field.setFont(font);
        field.setPreferredSize(new Dimension(100, 25));
        return field;
    }

    private void addInputRow(GridBagConstraints gbc, int row, String labelText, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel(labelText, JLabel.RIGHT), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(textField, gbc);
    }

    public double getMass() {
        return Double.parseDouble(massField.getText());
    }

    public double getRadius() {
        return Double.parseDouble(radiusField.getText());
    }

    public double getAngle() {
        return Double.parseDouble(angleField.getText());
    }

    public double getLineHeight() {
        return Double.parseDouble(heightField.getText());
    }

    public double getInertia() {
        return Double.parseDouble(inertiaField.getText());
    }

    public double getTimeStep() {
        return Double.parseDouble(dtField.getText());
    }

    public JButton getSimulateButton() {
        return simulateButton;
    }
}