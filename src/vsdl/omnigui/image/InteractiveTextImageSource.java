package vsdl.omnigui.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTextImageSource implements InteractiveImageSource {

    final String TEXT;
    final int LINE_HEIGHT;
    final Dimension AREA;
    final Color BG;
    final Color FG;
    final Runnable RUN;

    public InteractiveTextImageSource(String text, int lineHeight, Dimension area, Color foreGroundColor, Color backGroundColor, Runnable runnable) {
        TEXT = text;
        LINE_HEIGHT = lineHeight;
        AREA = area;
        BG = backGroundColor;
        FG = foreGroundColor;
        RUN = runnable;
    }

    @Override
    public BufferedImage image() {
        return TextImager.image(
                TEXT,
                LINE_HEIGHT,
                AREA.height,
                AREA.width,
                FG,
                BG
        );
    }

    @Override
    public void inform() {
        System.out.println("Information: " + TEXT);
    }

    @Override
    public void invoke() {
        RUN.run();
    }

    @Override
    public Dimension size() {
        return AREA;
    }
}
