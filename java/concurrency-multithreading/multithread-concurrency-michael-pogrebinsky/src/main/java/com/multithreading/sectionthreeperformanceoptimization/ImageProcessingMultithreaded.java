package com.multithreading.sectionthreeperformanceoptimization;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessingMultithreaded {

    public static final String SOURCE_FILE = "src" + File.separator + "main" + File.separator + "java" + File.separator + "com/multithreading/sectionthreeperformanceoptimization/latencyresources/output/resources" + File.separator + "many-flowers.jpg";
    public static final String DESTINATION_FILE = "src" + File.separator + "main" + File.separator + "java" + File.separator  + "com/multithreading/sectionthreeperformanceoptimization/latencyresources/output" + File.separator + "many-flowers.jpg";

    /**
     * Entry point for the image processing program.
     *
     * @param args The command line arguments
     * @throws IOException If there is an error reading the source file or writing the destination file
     */
    public static void main(String[] args) throws IOException {
//        System.out.println(new File(SOURCE_FILE).getAbsolutePath());
//        System.out.println(new File(SOURCE_FILE).exists());


        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
        recolorMultithreaded(originalImage, resultImage, 8);
        long endTime = System.currentTimeMillis();

        ImageIO.write(resultImage, "jpg", new File(DESTINATION_FILE));

        System.out.println("Multi thread time: " + (endTime - startTime));
    }

    /**
     * Recolors an image using multiple threads.
     *
     * @param originalImage the original image
     * @param resultImage the image to be recolored
     * @param numberOfThreads the number of threads to use
     */
    public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();

        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for(int i = 0; i < numberOfThreads; i++) {

            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int leftCorner = 0;
                int topCorner = threadMultiplier * height;
                recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
            });
            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Recolors an image using a single thread.
     *
     * @param originalImage the original image
     * @param resultImage the image to be recolored
     */
    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    /**
     * Recolors a specified region of an image.
     *
     * @param originalImage the original image
     * @param resultImage the image to be recolored
     * @param leftCorner the left corner of the region to be recolored
     * @param topCorner the top corner of the region to be recolored
     * @param width the width of the region to be recolored
     * @param height the height of the region to be recolored
     */
    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {
        for(int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            for(int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    /**
     * Recolors a single pixel in an image.
     *
     * @param originalImage the original image
     * @param resultImage the image to be recolored
     * @param x the x coordinate of the pixel to be recolored
     * @param y the y coordinate of the pixel to be recolored
     */
    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);
        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    /**
     * Sets the RGB value of a pixel in a BufferedImage.
     *
     * @param image the BufferedImage to set the pixel in
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param rgb the new RGB value of the pixel
     */
    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    /**
     * Determines if a color is a shade of gray.
     *
     * @param red the red component of the color
     * @param green the green component of the color
     * @param blue the blue component of the color
     * @return true if the color is a shade of gray, false otherwise
     */
    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    /**
     * Creates a new RGB value from the given red, green and blue components.
     *
     * @param red the red component of the color
     * @param green the green component of the color
     * @param blue the blue component of the color
     * @return a new RGB value created from the given components
     */
    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    /**
     * Retrieves the red component from the given RGB value.
     *
     * @param rgb the RGB value
     * @return the red component of the RGB value
     */
    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    /**
     * Retrieves the green component from the given RGB value.
     *
     * @param rgb the RGB value
     * @return the green component of the RGB value
     */
    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    /**
     * Retrieves the blue component from the given RGB value.
     *
     * @param rgb the RGB value
     * @return the blue component of the RGB value
     */
    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}
