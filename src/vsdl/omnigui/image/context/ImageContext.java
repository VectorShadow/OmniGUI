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
