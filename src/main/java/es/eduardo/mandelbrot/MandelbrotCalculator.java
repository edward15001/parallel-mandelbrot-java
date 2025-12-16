package es.eduardo.mandelbrot;

public class MandelbrotCalculator {

    public static int iterationsForPoint(double x0, double y0, int maxIterations) {
        double x = 0.0;
        double y = 0.0;
        int iter = 0;

        while (x * x + y * y <= 4.0 && iter < maxIterations) {
            double xTemp = x * x - y * y + x0;
            y = 2 * x * y + y0;
            x = xTemp;
            iter++;
        }
        return iter;
    }
}
