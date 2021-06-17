package image;

import org.junit.jupiter.api.Test;
import window.OmniPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static image.ImageScaler.*;

public class ImageScalerTest {

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

    @Test
    public void testGetMouseEventCoordinates() {
        int x = 15;
        int y = 22;
        MouseEvent mouseEvent =
                new MouseEvent(new OmniPanel(), 0, 0L, 0, x, y, 0, false);
        Point p = getMouseEventCoordinates(mouseEvent);
        assert p.x == x && p.y == y;
    }

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
}
