package es.eduardo.mandelbrot;

import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        MandelbrotConfig config = new MandelbrotConfig();

        config.width = 1920;
        config.height = 1080;
        config.maxIterations = 1000;

        System.out.println("Generating Mandelbrot fractal (sequential version)...");
        final BufferedImage[] imgSeq = new BufferedImage[1];

        long tSeq = Utils.measureMillis(() -> {
            imgSeq[0] = SequentialRenderer.render(config);
        });

        Utils.saveImage(imgSeq[0], "mandelbrot_sequential.png");
        System.out.println("Sequential time: " + tSeq + " ms");

        System.out.println("\n--- Parallel version (multiple threads) ---");

        int[] threadCounts = { 1, 2, 4, 8, 16 };

        for (int numThreads : threadCounts) {
            final BufferedImage[] imgPar = new BufferedImage[1];

            long tPar = Utils.measureMillis(() -> {
                try {
                    imgPar[0] = ParallelRendererExecutor.render(config, numThreads);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            String filename = "mandelbrot_parallel_" + numThreads + "threads.png";
            Utils.saveImage(imgPar[0], filename);

            double speedup = (double) tSeq / tPar;

            System.out.println("Threads: " + numThreads +
                    " | Parallel time: " + tPar + " ms" +
                    " | Speedup: " + String.format("%.2f", speedup));
        }

        System.out.println("\n--- ForkJoin version ---");

        final BufferedImage[] imgFJ = new BufferedImage[1];

        long tFJ = Utils.measureMillis(() -> {
            imgFJ[0] = ForkJoinRenderer.render(config);
        });

        Utils.saveImage(imgFJ[0], "mandelbrot_forkjoin.png");

        System.out.println("ForkJoin time: " + tFJ + " ms");
        System.out.println("Speedup vs sequential: " + String.format("%.2f", (double) tSeq / tFJ));

        System.out.println("\nEnd.");
    }
}
