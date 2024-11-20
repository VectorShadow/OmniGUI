package vsdl.omnigui.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageComposer {
    public static BufferedImage composeBlankImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public static void superimpose(BufferedImage subImage, BufferedImage ontoImage, Point offset) {
        for (int subImageX = 0; subImageX < subImage.getWidth(); ++subImageX) {
            for (int subImageY = 0; subImageY < subImage.getHeight(); ++subImageY) {
                ontoImage.setRGB(
                        subImageX + offset.x,
                        subImageY + offset.y,
                        subImage.getRGB(subImageX, subImageY)
                );
            }
        }
    }
}
