package vsdl.omnigui.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TextImager {

    private static final String DEFAULT_FONT_NAME = Font.DIALOG;
    private static final int DEFAULT_FONT_STYLE = Font.PLAIN;

    public static BufferedImage fromChar(
            char symbol,
            int fontSize,
            Color foreground,
            Color background,
            boolean formatAsText
    ) {
        return fromChar(
                symbol,
                new Font(DEFAULT_FONT_NAME, DEFAULT_FONT_STYLE, fontSize),
                foreground,
                background,
                formatAsText
        );
    }

    public static BufferedImage fromChar(
            char symbol,
            Font font,
            Color foreground,
            Color background,
            boolean formatAsText
    ) {
        final JLabel L = getRenderLabelBySize(font.getSize(), formatAsText);
        L.setFont(font);
        L.setBackground(background);
        L.setForeground(foreground);
        L.setText("" + symbol);
        final BufferedImage B = new BufferedImage(L.getWidth(), L.getHeight(), BufferedImage.TYPE_INT_RGB);
        L.paint(B.getGraphics());
        return B;
    }

    private static JLabel getRenderLabelBySize(int fontSize, boolean formatAsText) {
        JLabel ret = new JLabel();
        ret.setHorizontalAlignment(SwingConstants.CENTER);
        ret.setOpaque(true);
        int h = (int)((double)fontSize * 1.333);
        int w = formatAsText ? (int)((double)h * (10.0 / 16.0)) : h;
        ret.setSize(w, h);
        return ret;
    }
}
