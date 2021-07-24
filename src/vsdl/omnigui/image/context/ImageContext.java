package vsdl.omnigui.image.context;

import vsdl.omnigui.image.source.InteractiveImageSource;

import java.awt.*;

public class ImageContext {
    private final InteractiveImageSource IIS;
    private final Point ORIGIN;

    public ImageContext(InteractiveImageSource interactiveImageSource, Point origin) {
        IIS = interactiveImageSource;
        ORIGIN = origin;
    }

    public ImageContext(InteractiveImageSource interactiveImageSource, Dimension centerOnTargetCanvas) {
        IIS = interactiveImageSource;
        Dimension d = IIS.size();
        int x = centerOnTargetCanvas.width - d.width;
        int y = centerOnTargetCanvas.height - d.height;
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Image size exceeds canvas size" +
                    "(Image: " + d.width + "x" + d.height + ", " +
                    "Canvas: (" + centerOnTargetCanvas.width + "x" + centerOnTargetCanvas.height + ").");
        ORIGIN = new Point(x / 2, y / 2);
    }

    boolean contains(Point p) {
        return p.y >= ORIGIN.y && p.y < ORIGIN.y + IIS.size().height &&
                p.x >= ORIGIN.x && p.x < ORIGIN.x + IIS.size().width;
    }

    Point getOrigin() {
        return ORIGIN;
    }

    InteractiveImageSource getSource() {
        return IIS;
    }
}
