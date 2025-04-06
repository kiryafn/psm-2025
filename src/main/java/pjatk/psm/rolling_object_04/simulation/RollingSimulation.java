package pjatk.psm.rolling_object_04.simulation;

public class RollingSimulation {
    private static final double G = 9.81;

    private final double mass;
    private final double height;
    private final double radius;
    private final double alpha;
    private final double inertia;

    private final double planeLength;

    private double s;
    private double v;
    private double beta;
    private double omega;

    private final double a;
    private final double epsilon;

    public CoordinatesDatabase coordinatesDatabase = new CoordinatesDatabase();

    public RollingSimulation(double mass, double height, double radius,
                             double angleDeg, double inertiaFactor) {
        this.mass = mass;
        this.height = height;
        this.radius = radius;
        this.alpha = Math.toRadians(angleDeg);
        this.planeLength = height / Math.sin(alpha);
        this.inertia = inertiaFactor * mass * radius * radius;
        double denominator = 1.0 + (inertia / (mass * radius * radius));
        this.a = G * Math.sin(this.alpha) / denominator;
        this.epsilon = a / radius;

        reset();
    }

    private void getInclineLineCoordinates() {
        double xBottom = height * Math.cos(alpha) / Math.sin(alpha); //cot
        coordinatesDatabase.addInclineLineCoordinates(0d, height);
        coordinatesDatabase.addInclineLineCoordinates(xBottom, 0d);
    }

    private void reset() {
        s = 0d;
        v = 0d;
        beta = 0d;
        omega = 0d;

        coordinatesDatabase.reset();
    }

    private void calculateBallProjection(double xCenter, double yCenter) {
        for (double theta = 0; theta <= 2 * Math.PI; theta += 0.05) {
            double x = xCenter + radius * Math.cos(theta);
            double y = yCenter + radius * Math.sin(theta);
            coordinatesDatabase.addBallProjectionCoordinates(x, y);
        }
    }

    public void simulate(double dt) {
        reset();
        getInclineLineCoordinates();

        double time = 0d;

        while (true) {
            double xCenter = s * Math.cos(-alpha) - radius * Math.sin(-alpha);
            double yCenter = s * Math.sin(-alpha) + radius * Math.cos(-alpha) + height;

            if (xCenter >= 0 && yCenter >= 0) coordinatesDatabase.addCenterCoordinates(xCenter, yCenter);

            double xRing = radius * Math.sin(beta) + xCenter;
            double yRing = radius * Math.cos(beta) + yCenter;

            if (xRing >= 0 && yRing >= 0) coordinatesDatabase.addBallCoordinates(xRing, yRing);

            double Ep = mass * G * yCenter;
            double EkTrans = 0.5 * mass * (v * v);
            double EkRot = 0.5 * inertia * (omega * omega);
            double Et = Ep + EkTrans + EkRot;

            coordinatesDatabase.addEpCoordinates(time, Ep);
            coordinatesDatabase.addEkCoordinates(time, EkTrans + EkRot);
            coordinatesDatabase.addEtCoordinates(time, Et);

            double vHalf = v + a * dt / 2;
            double omegaHalf = omega + epsilon * dt / 2;

            s = s + vHalf * dt;
            v = v + a * dt;
            beta = beta + omegaHalf * dt;
            omega = omega + epsilon * dt;

            if (time == 0d) {
                calculateBallProjection(xCenter, yCenter);
            }

            if (yCenter <= 0) {
                break;
            }

            time += dt;

            if (s >= planeLength) {
                s = planeLength;
                break;
            }
        }
    }
}