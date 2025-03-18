package pjatk.psm.task02.ui;

import pjatk.psm.task02.simulations.EulerSimulation;
import pjatk.psm.task02.simulations.MidpointSimulation;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Trajectory Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        // Общий график
        ChartPanel chartPanel = new ChartPanel();

        // Панели для ввода для каждого метода
        InputPanel eulerInputPanel = new InputPanel("Euler Method");
        InputPanel midpointInputPanel = new InputPanel("Midpoint Method");

        // Обработчик для метода Эйлера
        eulerInputPanel.setOnSimulateListener((dt, m, k, sx0, sy0, vx0, vy0) -> {
            EulerSimulation simulation = new EulerSimulation(dt, m, k);
            simulation.simulate(sx0, sy0, vx0, vy0);

            // Добавляем траекторию на график (синий цвет)
            chartPanel.updateChart("Euler", simulation.xData, simulation.yData, "blue");
        });

        // Обработчик для метода Midpoint
        midpointInputPanel.setOnSimulateListener((dt, m, k, sx0, sy0, vx0, vy0) -> {
            MidpointSimulation simulation = new MidpointSimulation(dt, m, k);
            simulation.simulate(sx0, sy0, vx0, vy0);

            // Добавляем траекторию на график (красный цвет)
            chartPanel.updateChart("Midpoint", simulation.xData, simulation.yData, "red");
        });

        // Компоновка панелей
        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.add(eulerInputPanel);
        inputPanel.add(midpointInputPanel);

        add(inputPanel, BorderLayout.WEST); // Панель ввода слева
        add(chartPanel, BorderLayout.CENTER); // График справа
    }
}