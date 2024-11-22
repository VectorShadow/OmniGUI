package vsdl.omnigui.fixtures;

import vsdl.omnigui.image.source.InteractiveImageSource;
import vsdl.omnigui.image.source.InteractiveTextImageSource;
import vsdl.omnigui.image.source.TextImageSourceConfiguration;

import java.awt.*;

public class ImageSourceFixture {

    private static TextImageSourceConfiguration getTextImageSourceConfiguration() {
        return new TextImageSourceConfiguration(
                1,
                1,
                Color.BLACK,
                Color.WHITE
        );
    }

    public static InteractiveImageSource getImageSource() {
        return new InteractiveTextImageSource("test", getTextImageSourceConfiguration(), () -> {});
    }
}
