package image;


import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageScaler {

    private static int OP_TYPE = AffineTransformOp.TYPE_BILINEAR;

    /**
     * Find a point on a source image equivalent to a given point on an image scaled from that source image.
     * Intended for translating mouse clicks on a displayed image to their equivalent location on a base image.
     * @param scaledImage the image for which we have a point to translate
     * @param sourceImage the base image for the scaled image
     * @param scaledImagePoint the point on the scaled image to translate to the source image
     * @return a point on the source image equivalent to the the input point on the scaled image
     */
    public static Point descalePixelCoordinate(
            @NotNull BufferedImage scaledImage,
            @NotNull BufferedImage sourceImage,
            @NotNull Point scaledImagePoint) {
        double[] ratios = getAspectRatios(sourceImage, scaledImage);
        return new Point(
                (int) ((double) (scaledImagePoint.x) * ratios[1]),
                (int) ((double) (scaledImagePoint.y) * ratios[0])
        );
    }

    /**
     * Return the aspect ratios of two images.
     * @param img1 provides the numerator for the ratio
     * @param img2 provides the denominator for the ratio
     * @return an array containing the height ratio as the first element and the width ratio as the second
     */
    private static double[] getAspectRatios(@NotNull BufferedImage img1, @NotNull BufferedImage img2) {
        return new double[]{
                (double) (img1.getHeight()) / (double) (img2.getHeight()),
                (double) (img1.getWidth()) / (double) (img2.getWidth()),
        };
    }

    /**
     * Extract the coordinates of a MouseEvent.
     * @param e the MouseEvent for which coordinates are desired
     * @return a Point representing the coordinates of the MouseEvent
     */
    public static Point getMouseEventCoordinates(MouseEvent e) {
        return new Point(e.getX(), e.getY());
    }

    /**
     * Scale a source image to the size of a target image.
     * @param source the image to be scaled
     * @param target an image with the desired size
     * @return an image equivalent to the source image scaled to the size of the target image
     */
    public static BufferedImage resize(@NotNull BufferedImage source, @NotNull BufferedImage target) {
        double[] ratios = getAspectRatios(target, source);
        AffineTransform xform = new AffineTransform();
        xform.scale(ratios[1], ratios[0]);
        AffineTransformOp op = new AffineTransformOp(xform, OP_TYPE);
        return op.filter(source, target);
    }

    /**
     * Set the operation type for affine transforms for image scaling.
     * @param opType the operation type to use
     */
    public static void setOpType(int opType) {
        OP_TYPE = opType;
    }

}
