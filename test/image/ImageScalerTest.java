package image;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static image.ImageScaler.*;

public class ImageScalerTest {

    @Test
    public void testScaleImage() {
        int sourceHeight = 90;
        int sourceWidth = 160;
        int targetHeight = 50;
        int targetWidth = 80;
        BufferedImage sourceImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        BufferedImage destinationImage = resize(sourceImage, targetImage);
        assert destinationImage.getHeight() == targetHeight && destinationImage.getWidth() == targetWidth;
    }

    @Test
    public void testDescalePixelCoordinate() {
        int sourceHeight = 90;
        int sourceWidth = 160;
        int targetHeight = 50;
        int targetWidth = 80;
        BufferedImage sourceImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Point scaledPoint = new Point(80, 45);
        Point sourcePoint = descalePixelCoordinate(sourceImage, targetImage, scaledPoint);
        assert sourcePoint.x == 40 && sourcePoint.y == 25;
    }
}
