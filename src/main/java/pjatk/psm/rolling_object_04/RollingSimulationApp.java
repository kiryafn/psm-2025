package pjatk.psm.rolling_object_04;

import pjatk.psm.rolling_object_04.ui.RollingUI;

import javax.swing.*;

public class RollingSimulationApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RollingUI ui = new RollingUI();
            ui.createAndShowGUI();
        });
    }
}