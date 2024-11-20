package vsdl.omnigui.exec;

import vsdl.omnigui.api.Gui;
import vsdl.omnigui.fixtures.TestEventListenerFixture;
import vsdl.omnigui.image.source.HistoricalTextDisplayAreaImageSource;
import vsdl.omnigui.image.source.InteractiveImageSource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TerminalDriver {

    private static void refreshScreen(Gui gui, InteractiveImageSource imageSource) {
        BufferedImage bufferedImage = imageSource.image();
        gui.paintCanvas(
                bufferedImage,
                new Point(0,0),
                -1
        );
        gui.updateFrameImage();
    }

    public static void main(String[] args) {
        Gui gui = new Gui(new Dimension(800, 320));
        gui.createFrame(false, "Terminal", null);
        gui.addEventListener(TestEventListenerFixture.getFullscreenToggleKeyListener());
        gui.addEventListener(TestEventListenerFixture.getDefaultCloseWindowListener());
        HistoricalTextDisplayAreaImageSource imageSource = new HistoricalTextDisplayAreaImageSource(
                9,
                32,
                new Dimension(800, 32),
                Color.BLACK,
                Color.GREEN,
                Color.GRAY,
                false
        );
        imageSource.addMessage("Hello!");
        refreshScreen(gui, imageSource);
        try {
            Thread.sleep(1250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        imageSource.addMessage("Test Message");
        refreshScreen(gui, imageSource);
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
            imageSource.addMessage("Buffering...");
            refreshScreen(gui, imageSource);
        }
    }
}
