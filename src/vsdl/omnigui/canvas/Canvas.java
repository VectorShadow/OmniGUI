package vsdl.omnigui.canvas;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas {

    private final int HEIGHT;
    private final int WIDTH;

    private BufferedImage image;

    public Canvas(int height, int width) {
        if (height <=0 || width <= 0)
            throw new IllegalArgumentException("Height and Width must both be greater than zero.");
        HEIGHT = height;
        WIDTH = width;
        clearImage();
    }

    public void clearImage() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void updateImage(BufferedImage subImage, Point origin, int rgbIgnore) {
        int x = origin.x;
        int y = origin.y;
        if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
            throw new IllegalArgumentException(
                    "Origin out of bounds - x:" + x + "[0-" + WIDTH + "], y:" + y + "[0-" + HEIGHT + "]"
            );
        }
        for (int i = 0; i < subImage.getWidth(); ++i) {
            if (i + x >= WIDTH) break;
            for (int j = 0; j < subImage.getHeight(); ++j) {
                if (j + y >= HEIGHT) break;
                int sourceRGB = subImage.getRGB(i, j);
                if (sourceRGB != rgbIgnore)
                    image.setRGB(i + x, j + y, sourceRGB);
            }
        }
    }

}
