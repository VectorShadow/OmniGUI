package vsdl.omnigui.image.source;

import java.awt.*;

public class TextImageSourceConfiguration {

    private final int lineHeight;
    private final Dimension textAreaDimension;
    private final Color textBackgroundColor;
    private final Color textForegroundColor;

    public TextImageSourceConfiguration(int lineHeight, int width, Color textBackgroundColor, Color textForegroundColor) {
        this(lineHeight, new Dimension(width, lineHeight), textBackgroundColor, textForegroundColor);
    }

    public TextImageSourceConfiguration(int lineHeight, Dimension textAreaDimension, Color textBackgroundColor, Color textForegroundColor) {
        this.lineHeight = lineHeight;
        this.textAreaDimension = textAreaDimension;
        this.textBackgroundColor = textBackgroundColor;
        this.textForegroundColor = textForegroundColor;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public Dimension getTextAreaDimension() {
        return textAreaDimension;
    }

    public Color getTextBackgroundColor() {
        return textBackgroundColor;
    }

    public Color getTextForegroundColor() {
        return textForegroundColor;
    }

    public TextImageSourceConfiguration clone() {
        return new TextImageSourceConfiguration(lineHeight, textAreaDimension, textBackgroundColor, textForegroundColor);
    }
}
