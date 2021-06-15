package image;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageScalerTest {

    @Test
    public void testScaleImage() {
        int sourceHeight = 90;
        int sourceWidth = 160;
        int targetHeight = 50;
        int targetWidth = 80;
        BufferedImage sourceImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        BufferedImage destinationImage = ImageScaler.resize(sourceImage, targetImage);
        assert destinationImage.getHeight() == targetHeight && destinationImage.getWidth() == targetWidth;
    }
}
