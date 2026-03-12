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

    public static int colorFromIterations(int iter, int maxIterations) {
        if (iter == maxIterations) {
            return 0x000000; // Interior is black
        }
        
        // Calculate brightness based on iterations (normalized 0 to 1)
        double t = (double) iter / maxIterations;
        
        // Use a power function to make the "glow" around the fractal more visible
        // Lower exponents (e.g., 0.5) make the gradient reach further.
        int brightness = (int) (255 * Math.pow(t, 0.5));
        
        // Yellow color: R and G are high, B is 0
        int r = brightness;
        int g = brightness;
        int b = 0;
        
        return (r << 16) | (g << 8) | b;
    }
}
