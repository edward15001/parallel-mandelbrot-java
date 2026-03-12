
package es.eduardo.mandelbrot;

import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinRenderer {

    private static class RenderTask extends RecursiveAction {
        private final MandelbrotConfig config;
        private final BufferedImage image;
        private final int startRow;
        private final int endRow;
        private final int threshold = 50; // número mínimo de filas por tarea

        RenderTask(MandelbrotConfig config, BufferedImage image, int startRow, int endRow) {
            this.config = config;
            this.image = image;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected void compute() {
            int numRows = endRow - startRow;

            if (numRows <= threshold) {
                // Calcular directamente
                for (int py = startRow; py < endRow; py++) {
                    double y0 = config.yMin + py * (config.yMax - config.yMin) / (config.height - 1);

                    for (int px = 0; px < config.width; px++) {
                        double x0 = config.xMin + px * (config.xMax - config.xMin) / (config.width - 1);

                        int iter = MandelbrotCalculator.iterationsForPoint(
                                x0, y0, config.maxIterations);

                        int color = MandelbrotCalculator.colorFromIterations(iter, config.maxIterations);
                        image.setRGB(px, py, color);
                    }
                }
            } else {
                // Dividir en dos tareas
                int mid = (startRow + endRow) / 2;

                RenderTask top = new RenderTask(config, image, startRow, mid);
                RenderTask bottom = new RenderTask(config, image, mid, endRow);

                invokeAll(top, bottom);
            }
        }
    }

    public static BufferedImage render(MandelbrotConfig config) {
        BufferedImage image = new BufferedImage(
                config.width,
                config.height,
                BufferedImage.TYPE_INT_RGB);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        RenderTask task = new RenderTask(config, image, 0, config.height);

        pool.invoke(task);

        return image;
    }
}
