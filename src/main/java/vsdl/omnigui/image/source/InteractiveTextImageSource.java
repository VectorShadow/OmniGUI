package vsdl.omnigui.image.source;

import org.jetbrains.annotations.NotNull;
import vsdl.omnigui.image.TextImager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class InteractiveTextImageSource implements InteractiveImageSource {

    final String TEXT;
    final TextImageSourceConfiguration CONFIG;
    final Runnable RUN;

    public InteractiveTextImageSource(String text, TextImageSourceConfiguration config, Runnable runnable) {
        TEXT = text;
        CONFIG = config;
        RUN = runnable;
    }

    @Override
    public BufferedImage image() {
        return TextImager.image(
                TEXT,
                CONFIG
        );
    }

    @Override
    public void inform(@NotNull Point imageCoordinates) {
        System.out.println("Information: " + TEXT);
    }

    @Override
    public void input(KeyEvent e) {
        System.out.println("Hotkey pressed.");
    }

    @Override
    public void invoke(@NotNull Point imageCoordinates) {
        RUN.run();
    }

    @Override
    public Dimension size() {
        return CONFIG.getTextAreaDimension();
    }
}
