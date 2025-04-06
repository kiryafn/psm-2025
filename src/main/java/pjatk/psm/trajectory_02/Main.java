package pjatk.psm.trajectory_02;

import pjatk.psm.trajectory_02.ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
