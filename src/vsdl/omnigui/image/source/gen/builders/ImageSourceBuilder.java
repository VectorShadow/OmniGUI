package vsdl.omnigui.image.source.gen.builders;

import java.awt.*;

public abstract class ImageSourceBuilder {
    private String title;

    private Color[] defaultColors;

    public ImageSourceBuilder(Color[] colors) {
        this.defaultColors = colors;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
