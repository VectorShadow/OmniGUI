package vsdl.omnigui.exec;

import vsdl.omnigui.api.Gui;
import vsdl.omnigui.image.source.HistoricalTextDisplayAreaImageSource;

import java.awt.*;

public class TerminalDriver {
    public static void main(String[] args) {
        Gui gui = new Gui(new Dimension(800, 320));
        gui.createFrame(false, "Terminal", null);
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
        gui.paintCanvas(imageSource.image(), new Point(0,0), -1);
        gui.updateFrameImage();
        try {
            Thread.sleep(1250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        imageSource.addMessage("Test Message");
        gui.paintCanvas(imageSource.image(), new Point(0,0), -1);
        gui.updateFrameImage();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
            imageSource.addMessage("Buffering...");
            gui.paintCanvas(imageSource.image(), new Point(0,0), -1);
            gui.updateFrameImage();
        }
    }
}
