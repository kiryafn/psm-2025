package pjatk.psm.task02;

import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import java.util.List;

public class ProjectileMotion {
    private static final double g = 9.81;
    private static final double k = 0.1;
    private static final double dt = 0.1;

    public static void main(String[] args) {
        List<Double> xEuler = new ArrayList<>();
        List<Double> yEuler = new ArrayList<>();
        List<Double> xMidpoint = new ArrayList<>();
        List<Double> yMidpoint = new ArrayList<>();

        double x = 0, y = 0;
        double vx = 10, vy = 10;

        System.out.println("Euler Method:");
        simulateEuler(x, y, vx, vy, xEuler, yEuler);

        System.out.println("\nMidpoint Method:");
        simulateMidpoint(x, y, vx, vy, xMidpoint, yMidpoint);

        plotTrajectories(xEuler, yEuler, xMidpoint, yMidpoint);
    }

    private static void simulateEuler(double x, double y, double vx, double vy, List<Double> xData, List<Double> yData) {
        while (y >= 0) {

            xData.add(x);
            yData.add(y);

            double ax = -k * vx;
            double ay = -k * vy - g;

            vx += ax * dt;
            vy += ay * dt;

            x += vx * dt;
            y += vy * dt;

            System.out.printf("x: %.2f, y: %.2f, vx: %.2f, vy: %.2f\n", x, y, vx, vy);
        }
    }

    private static void simulateMidpoint(double x, double y, double vx, double vy, List<Double> xData, List<Double> yData) {
        while (y >= 0) {
            xData.add(x);
            yData.add(y);

            double ax1 = -k * vx;
            double ay1 = -k * vy - g;

            double vxMid = vx + ax1 * dt / 2;
            double vyMid = vy + ay1 * dt / 2;

            double ax2 = -k * vxMid;
            double ay2 = -k * vyMid - g;

            vx += ax2 * dt;
            vy += ay2 * dt;

            x += vx * dt;
            y += vy * dt;

            System.out.printf("x: %.2f, y: %.2f, vx: %.2f, vy: %.2f\n", x, y, vx, vy);
        }
    }

    private static void plotTrajectories(List<Double> xEuler, List<Double> yEuler, List<Double> xMidpoint, List<Double> yMidpoint) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Projectile Motion")
                .xAxisTitle("x (Distance)")
                .yAxisTitle("y (Height)")
                .build();

        XYSeries eulerSeries = chart.addSeries("Euler Method", xEuler, yEuler);
        eulerSeries.setMarker(SeriesMarkers.CIRCLE);

        XYSeries midpointSeries = chart.addSeries("Midpoint Method", xMidpoint, yMidpoint);
        midpointSeries.setMarker(SeriesMarkers.SQUARE);

        new SwingWrapper<>(chart).displayChart();
    }
}