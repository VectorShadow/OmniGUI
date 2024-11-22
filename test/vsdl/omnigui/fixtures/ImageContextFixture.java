package vsdl.omnigui.fixtures;

import vsdl.omnigui.image.context.ImageContext;

import java.awt.*;

import static vsdl.omnigui.fixtures.ImageSourceFixture.getImageSource;

public class ImageContextFixture {
    public static ImageContext getImageContext() {
        return new ImageContext(getImageSource(), new Point(0,0));
    }
}
