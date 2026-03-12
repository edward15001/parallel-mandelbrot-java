package es.eduardo.mandelbrot;

import java.awt.image.BufferedImage;

public class SequentialRenderer {

    public static BufferedImage render(MandelbrotConfig config) {
        BufferedImage image = new BufferedImage(
                config.width,
                config.height,
                BufferedImage.TYPE_INT_RGB);

        for (int py = 0; py < config.height; py++) {
            double y0 = config.yMin + py * (config.yMax - config.yMin) / (config.height - 1);

            for (int px = 0; px < config.width; px++) {
                double x0 = config.xMin + px * (config.xMax - config.xMin) / (config.width - 1);

                int iter = MandelbrotCalculator.iterationsForPoint(x0, y0, config.maxIterations);
                int color = MandelbrotCalculator.colorFromIterations(iter, config.maxIterations);
                image.setRGB(px, py, color);
            }
        }

        return image;
    }
}
