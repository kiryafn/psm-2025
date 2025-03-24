package pjatk.psm.task03;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class s31722_LAB3_PSM extends JFrame {
    private JTextField tfMass, tfLen, tfAngle, tfTime, tfDt;

    private XYSeries heunEpSeries, heunEkSeries, heunOmegaSeries;
    private XYSeries heunEtSeries;

    private XYSeries rk4EpSeries, rk4EkSeries, rk4OmegaSeries;
    private XYSeries rk4EtSeries;

    private JFreeChart heunEnergyChart, heunPhaseChart;
    private JFreeChart rk4EnergyChart, rk4PhaseChart;

    private CardLayout cardLayout; // switch between  Heun-панелью и RK4-панелью
    private JPanel mainPanel;

    public s31722_LAB3_PSM() {
        super("s31722_LAB3_PSM");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1400, 700);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Mass:"));
        tfMass = new JTextField("10", 4);
        inputPanel.add(tfMass);

        inputPanel.add(new JLabel("Length:"));
        tfLen = new JTextField("1", 4);
        inputPanel.add(tfLen);

        inputPanel.add(new JLabel("Angle (°):"));
        tfAngle = new JTextField("45", 4);
        inputPanel.add(tfAngle);

        inputPanel.add(new JLabel("Time:"));
        tfTime = new JTextField("10", 4);
        inputPanel.add(tfTime);

        inputPanel.add(new JLabel("Time Step:"));
        tfDt = new JTextField("0.1", 4);
        inputPanel.add(tfDt);

        JButton simulateBtn = new JButton("Simulate");
        inputPanel.add(simulateBtn);

        JButton heunBtn = new JButton("Heun");
        JButton rk4Btn = new JButton("RK4");
        inputPanel.add(heunBtn);
        inputPanel.add(rk4Btn);

        JButton showTotalBtn = new JButton("Show Total Energy");
        inputPanel.add(showTotalBtn);

        add(inputPanel, BorderLayout.SOUTH);

        heunEpSeries   = new XYSeries("Ep");
        heunEkSeries   = new XYSeries("Ek");
        heunOmegaSeries= new XYSeries("Omega");
        heunEtSeries   = new XYSeries("Et");

        rk4EpSeries    = new XYSeries("Ep");
        rk4EkSeries    = new XYSeries("Ek");
        rk4OmegaSeries = new XYSeries("Omega");
        rk4EtSeries    = new XYSeries("Et");

        heunEnergyChart = createEnergyChart("Pendulum Heun's Energies Motion",
                heunEpSeries, heunEkSeries  );
        heunPhaseChart = createPhaseChart("Pendulum Heun Omega", heunOmegaSeries);

        rk4EnergyChart = createEnergyChart("Pendulum RK4 Energies Motion",
                rk4EpSeries, rk4EkSeries  );
        rk4PhaseChart = createPhaseChart("Pendulum RK4 Omega", rk4OmegaSeries);

        XYLineAndShapeRenderer energyRenderer = createEnergyRenderer();
        heunEnergyChart.getXYPlot().setRenderer(energyRenderer);
        rk4EnergyChart.getXYPlot().setRenderer(energyRenderer);

        XYLineAndShapeRenderer omegaRenderer = createOmegaRenderer();
        heunPhaseChart.getXYPlot().setRenderer(omegaRenderer);
        rk4PhaseChart.getXYPlot().setRenderer(omegaRenderer);

        JPanel heunPanel = new JPanel(new GridLayout(1, 2));
        heunPanel.add(new ChartPanel(heunEnergyChart));
        heunPanel.add(new ChartPanel(heunPhaseChart));

        JPanel rk4Panel = new JPanel(new GridLayout(1, 2));
        rk4Panel.add(new ChartPanel(rk4EnergyChart));
        rk4Panel.add(new ChartPanel(rk4PhaseChart));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(heunPanel, "HEUN");
        mainPanel.add(rk4Panel, "RK4");

        add(mainPanel, BorderLayout.CENTER);

        simulateBtn.addActionListener(e -> simulate());

        heunBtn.addActionListener(e -> cardLayout.show(mainPanel, "HEUN"));
        rk4Btn.addActionListener(e -> cardLayout.show(mainPanel, "RK4"));

        showTotalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XYPlot heunPlot = heunEnergyChart.getXYPlot();
                XYSeriesCollection heunDataset = (XYSeriesCollection) heunPlot.getDataset();
                if (heunDataset.getSeriesCount() < 3) {
                    heunDataset.addSeries(heunEtSeries);
                }

                XYPlot rk4Plot = rk4EnergyChart.getXYPlot();
                XYSeriesCollection rk4Dataset = (XYSeriesCollection) rk4Plot.getDataset();
                if (rk4Dataset.getSeriesCount() < 3) {
                    rk4Dataset.addSeries(rk4EtSeries);
                }
            }
        });
    }
    private JFreeChart createEnergyChart(String title, XYSeries ep, XYSeries ek) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(ep);
        dataset.addSeries(ek);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Time (s)",
                "Energy (J)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        stylizeChart(chart);
        return chart;
    }

    private JFreeChart createPhaseChart(String title, XYSeries phase) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(phase);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Alpha (rad)",
                "Omega (rad/s)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        stylizeChart(chart);
        return chart;
    }
    private void stylizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.getHSBColor(210,210,210));
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        plot.setRenderer(renderer);
    }

    private void simulate() {
        try {
            double m = parseInput(tfMass);
            double L = parseInput(tfLen);
            double angle = Math.toRadians(parseInput(tfAngle));
            double T = parseInput(tfTime);
            double dt = parseInput(tfDt);
            validateInputs(m, L, T, dt);
            clearAllSeries();
            runSimulation(m, L, angle, T, dt);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void runSimulation(double m, double L, double angle, double T, double dt) {
        int steps = (int) (T / dt);
        double g = 9.81;
        double alphaHeun = angle, omegaHeun = 0;
        double alphaRK4 = angle, omegaRK4 = 0;
        for (int i = 0; i <= steps; i++) {
            double time = i * dt;
            addEnergyAndPhase(heunEpSeries, heunEkSeries, heunEtSeries,
                    heunOmegaSeries, time, m, g, L, alphaHeun, omegaHeun);

            addEnergyAndPhase(rk4EpSeries, rk4EkSeries, rk4EtSeries,
                    rk4OmegaSeries, time, m, g, L, alphaRK4, omegaRK4);
            if (i < steps) {
                double[] heun = heunStep(alphaHeun, omegaHeun, dt, g, L);
                alphaHeun = heun[0];
                omegaHeun = heun[1];

                double[] rk4 = rk4Step(alphaRK4, omegaRK4, dt, g, L);
                alphaRK4 = rk4[0];
                omegaRK4 = rk4[1];
            }
        }
    }
    private void addEnergyAndPhase(
            XYSeries epSeries, XYSeries ekSeries, XYSeries etSeries,
            XYSeries omegaSeries,
            double time, double m, double g, double L,
            double alpha, double omega) {

        double ePot = m * g * L * (1 - Math.cos(alpha));
        double eKin = 0.5 * m * L * L * omega * omega;
        double eTot = ePot + eKin;

        epSeries.add(time, ePot);
        ekSeries.add(time, eKin);

        if (etSeries != null) {
            etSeries.add(time, eTot);
        }

        if (omegaSeries != null) {
            omegaSeries.add(alpha, omega);
        }
    }

    private double[] heunStep(double alpha, double omega, double dt, double g, double L) {
        double slopeAlpha1 = omega;
        double slopeOmega1 = -(g / L) * Math.sin(alpha);

        double predictedAlpha = alpha + dt * slopeAlpha1;
        double predictedOmega = omega + dt * slopeOmega1;

        double slopeAlpha2 = predictedOmega;
        double slopeOmega2 = -(g / L) * Math.sin(predictedAlpha);

        alpha += 0.5 * dt * (slopeAlpha1 + slopeAlpha2);
        omega += 0.5 * dt * (slopeOmega1 + slopeOmega2);

        return new double[]{alpha, omega};
    }

    private double[] rk4Step(double currentAlpha, double currentOmega, double dt, double g, double L) {
        double dAlpha1 = currentOmega;
        double dOmega1 = -(g / L) * Math.sin(currentAlpha);

        double dAlpha2 = currentOmega + 0.5 * dt * dOmega1;
        double dOmega2 = -(g / L) * Math.sin(currentAlpha + 0.5 * dt * dAlpha1);

        double dAlpha3 = currentOmega + 0.5 * dt * dOmega2;
        double dOmega3 = -(g / L) * Math.sin(currentAlpha + 0.5 * dt * dAlpha2);

        double dAlpha4 = currentOmega + dt * dOmega3;
        double dOmega4 = -(g / L) * Math.sin(currentAlpha + dt * dAlpha3);

        double nextAlpha = currentAlpha + (dt / 6.0) * (dAlpha1 + 2 * dAlpha2 + 2 * dAlpha3 + dAlpha4);
        double nextOmega = currentOmega + (dt / 6.0) * (dOmega1 + 2 * dOmega2 + 2 * dOmega3 + dOmega4);

        return new double[]{nextAlpha, nextOmega};
    }

    private double parseInput(JTextField field) {
        return Double.parseDouble(field.getText());
    }

    private void validateInputs(double m, double L, double T, double dt) {
        if (m <= 0 || L <= 0 || T <= 0 || dt <= 0)
            throw new IllegalArgumentException("All values must be > 0");
    }

    private void clearAllSeries() {
        heunEpSeries.clear();
        heunEkSeries.clear();
        heunOmegaSeries.clear();
        heunEtSeries.clear();

        rk4EpSeries.clear();
        rk4EkSeries.clear();
        rk4OmegaSeries.clear();
        rk4EtSeries.clear();
    }

    private XYLineAndShapeRenderer createOmegaRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 4, 4));
        return renderer;
    }

    private XYLineAndShapeRenderer createEnergyRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        //renderer.setSeriesPaint(0,Color.BLACK);
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);
        return renderer;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new s31722_LAB3_PSM().setVisible(true);
        });
    }
}