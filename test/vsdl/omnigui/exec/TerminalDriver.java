package vsdl.omnigui.exec;

import vsdl.omnigui.api.Gui;
import vsdl.omnigui.api.listeners.ListenerProvider;
import vsdl.omnigui.api.message.Messenger;
import vsdl.omnigui.image.context.ImageContext;
import vsdl.omnigui.image.context.ImageContextProfile;
import vsdl.omnigui.image.context.ImageContextProfileBuilder;
import vsdl.omnigui.image.source.HistoricalTextDisplayAreaImageSource;
import vsdl.omnigui.image.source.TextEntryFieldImageSource;
import vsdl.omnigui.image.source.TextImageSourceConfiguration;

import java.awt.*;

public class TerminalDriver {

    public static void main(String[] args) {
        Gui gui = new Gui(new Dimension(800, 320));
        gui.createFrame(false, "Terminal", null);
        HistoricalTextDisplayAreaImageSource terminalOut = new HistoricalTextDisplayAreaImageSource(
                9,
                new TextImageSourceConfiguration(
                        32,
                        800,
                        Color.BLACK,
                        Color.GRAY
                ),
                Color.GREEN,
                false
        );
        terminalOut.addMessage("Hello!");
        TextEntryFieldImageSource terminalIn = new TextEntryFieldImageSource(
                new TextImageSourceConfiguration(
                        32,
                        800,
                        Color.DARK_GRAY,
                        Color.CYAN
                ),
                "> "
        );
        Messenger messenger = new Messenger();
        messenger.addDestination(terminalOut);
        terminalIn.addMessenger(messenger);

        ImageContextProfile icp = ImageContextProfileBuilder.start()
                .appendImageContext(new ImageContext(terminalOut, new Point(0,0)))
                .appendImageContext(new ImageContext(terminalIn, new Point(0,terminalOut.size().height)))
                .build();


        gui.addEventListener(ListenerProvider.getFullscreenToggleKeyListener());
        gui.addEventListener(ListenerProvider.getDefaultCloseWindowListener());
        gui.addEventListener(ListenerProvider.getImageContextProfileInputKeyListener(icp));

        icp.startAutoRepaintDaemon(gui, 25);

        try {
            Thread.sleep(1250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        terminalOut.addMessage("Test Message");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
            terminalOut.addMessage("Buffering...");
        }
    }
}
