package image;


import org.jetbrains.annotations.NotNull;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageScaler {

    private static int OP_TYPE = AffineTransformOp.TYPE_BILINEAR;

    public static void setOpType(int opType) {
        OP_TYPE = opType;
    }

    public static BufferedImage resize(@NotNull BufferedImage source, @NotNull BufferedImage target) {
        double hRatio = (double) target.getHeight() / (double) source.getHeight();
        double wRatio = (double) target.getWidth() / (double) source.getWidth();
        AffineTransform xform = new AffineTransform();
        xform.scale(wRatio, hRatio);
        AffineTransformOp op = new AffineTransformOp(xform, OP_TYPE);
        return op.filter(source, target);
    }
}
