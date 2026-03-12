package es.eduardo.mandelbrot;

import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelRendererExecutor {

    public static BufferedImage render(MandelbrotConfig config, int numThreads) throws InterruptedException {

        BufferedImage image = new BufferedImage(
                config.width,
                config.height,
                BufferedImage.TYPE_INT_RGB);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(config.height);

        for (int py = 0; py < config.height; py++) {
            final int row = py;

            executor.submit(() -> {
                double y0 = config.yMin + row * (config.yMax - config.yMin) / (config.height - 1);

                for (int px = 0; px < config.width; px++) {
                    double x0 = config.xMin + px * (config.xMax - config.xMin) / (config.width - 1);

                    int iter = MandelbrotCalculator.iterationsForPoint(x0, y0, config.maxIterations);
                    int color = MandelbrotCalculator.colorFromIterations(iter, config.maxIterations);

                    image.setRGB(px, row, color);
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();
        return image;
    }
}
