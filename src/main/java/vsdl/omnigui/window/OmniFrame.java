package vsdl.omnigui.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

//todo - much more here
public class OmniFrame extends JFrame {

    private boolean isInFullscreenMode;

    private Point windowedOrigin;

    private final OmniPanel panel;

    public OmniFrame(boolean fullScreenMode) {
        isInFullscreenMode = fullScreenMode;
        panel = new OmniPanel(fullScreenMode);
        addComponentListener(
                new ComponentAdapter() {
                    public void componentResized(ComponentEvent e) {
                        /*
                         * When we resize this Window, we want to update the displayed image.
                         * In particular, we need to know the new contentPane size and resize the outputPanel to match.
                         * This will invoke the outputPanel's overridden setSize method which rescales the image
                         * and repaints the component.
                         */
                        Dimension target = getContentPane().getSize();
                        if (target.getHeight() <= 0 || target.getWidth() <= 0) return;
                        panel.setPreferredSize(target);
                        panel.setSize(target);
                        setContentPane(panel);
                    }

                    @Override
                    public void componentMoved(ComponentEvent e) {
                        if (!isInFullscreenMode) windowedOrigin = getLocationOnScreen();
                    }
                }
        );
        //allow explicit handling of VK_TAB
        setFocusTraversalKeysEnabled(false);
        setVisible(true);
        windowedOrigin = this.getLocationOnScreen();
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

    public BufferedImage getBlankPanelImage() {
        return panel.getBlankScaledImage();
    }

    public void toggleFullScreenMode() {
        isInFullscreenMode = !isInFullscreenMode;
        initializeContents();
        if (!isInFullscreenMode) this.setLocation(windowedOrigin);
    }

    public void updatePanelImage(BufferedImage bufferedImage) {
        panel.setImage(bufferedImage);
        panel.repaint();
    }

}
