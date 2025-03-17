package pjatk.psm.task01;

public class Sin {
    public static void main(String[] args) {
        System.out.println("sin(2) = ");
        System.out.println(Math.sin(2));
        System.out.println(sinInRadians(2, 100));
        System.out.println(sinInDegrees(114.4, 100));
        System.out.println("-----------");
        System.out.println("sin(1) = ");
        System.out.println(Math.sin(1));
        System.out.println(sinInRadians(1, 100));
        System.out.println(sinInDegrees(57.2, 100));
    }

    //x^{(2n+1)} - numerator
    //(2n+1)! - denominator
    public static double sinInRadians(double x, int terms) {
        double result = 0;
        double term = x;
        int sign = 1;

        for (int n = 0; n < terms; n++) {
            result += sign * term;
            term *= x * x / ((2 * n + 2) * (2 * n + 3));
            sign = -sign;
        }

        return result;
    }

    public static double sinInDegrees(double x, int terms) {
        double radians = Math.toRadians(x);
        return sinInRadians(radians, terms);
    }
}