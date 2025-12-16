package es.eduardo.mandelbrot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils {

    public static void saveImage(BufferedImage image, String filename) {
        try {
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Image saved in: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long measureMillis(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        return end - start;
    }
}
