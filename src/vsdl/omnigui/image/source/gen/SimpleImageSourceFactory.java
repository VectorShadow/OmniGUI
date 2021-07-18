package vsdl.omnigui.image.source.gen;

import vsdl.omnigui.image.source.SimpleInputDialogImageSource;
import vsdl.omnigui.image.source.SimpleMenuImageSource;
import vsdl.omnigui.image.source.gen.builders.ImageSourceBuilder;
import vsdl.omnigui.image.source.gen.builders.SimpleInputDialogImageSourceBuilder;
import vsdl.omnigui.image.source.gen.builders.SimpleMenuImageSourceBuilder;

import java.awt.*;

public class SimpleImageSourceFactory {

    public static final int BG_IDX = 0;
    public static final int FG_IDX = 1;
    public static final int DIS_IDX = 2;
    public static final int EN_IDX = 3;

    private static final Color[] DEFAULT_COLORS = {Color.BLACK, Color.WHITE, Color.DARK_GRAY,  Color.LIGHT_GRAY};

    private Color[] colors = DEFAULT_COLORS;

    private final Class imageSourceClass;

    private SimpleImageSourceFactory(Class imageSourceClass) {
        this.imageSourceClass = imageSourceClass;
    }

    public static SimpleImageSourceFactory start(Class imageSourceClass) {
        return new SimpleImageSourceFactory(imageSourceClass);
    }

    public void setDefaultBackground(Color c) {
        colors[BG_IDX] = c;
    }

    public void setDefaultForeground(Color c) {
        colors[FG_IDX] = c;
    }

    public void setDefaultDisabled(Color c) {
        colors[DIS_IDX] = c;
    }

    public void setDefaultEnabled(Color c) {
        colors[EN_IDX] = c;
    }

    public ImageSourceBuilder manufacture() {
        if (imageSourceClass.equals(SimpleInputDialogImageSource.class))
            return new SimpleInputDialogImageSourceBuilder(colors);
        if (imageSourceClass.equals(SimpleMenuImageSource.class))
            return new SimpleMenuImageSourceBuilder(colors);
        throw new IllegalStateException("Unknown class: " + imageSourceClass);
    }
}
