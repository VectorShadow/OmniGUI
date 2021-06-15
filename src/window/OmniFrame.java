package window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

//todo - much more here
public class OmniFrame extends JFrame {

    private boolean isInFullscreenMode;

    private OmniPanel panel;

    public OmniFrame(boolean fullScreenMode) {
        isInFullscreenMode = fullScreenMode;
        panel = new OmniPanel();
        addComponentListener(
                new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        /**
                         * When we resize this Window, we want to update the displayed image.
                         * In particular, we need to know the new contentPane size and resize the outputPanel to match.
                         * This will invoke the outputPanel's overridden setSize method which rescales the image
                         * and repaints the component.
                         */
                        Dimension target = getContentPane().getSize();
                        panel.setPreferredSize(target);
                        panel.setSize(target);
                        setContentPane(panel);
                    }
                }
        );
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panel);
        initializeContents();
    }

    private void initializeContents() {
        panel.setFullscreenMode(isInFullscreenMode);
        //setUndecorated(isInFullscreenMode); //todo - undecorated requires frame to be undisplayable.
        pack();
        if (isInFullscreenMode) {
            setExtendedState(Frame.MAXIMIZED_BOTH);
        } else {
            setExtendedState(Frame.NORMAL);
            setLocation(ScreenTools.centeredOrigin(getSize()));
        }
    }

    public void toggleFullScreenMode() {
        isInFullscreenMode = !isInFullscreenMode;
        initializeContents();
    }

    public void updatePanelImage(BufferedImage bufferedImage) {
        panel.setImage(bufferedImage);
        panel.repaint();
    }

}
