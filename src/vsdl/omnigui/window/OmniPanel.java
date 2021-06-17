package vsdl.omnigui.window;

import vsdl.omnigui.image.ImageScaler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class OmniPanel extends JPanel {

    private static Dimension windowedDimension = ScreenTools.getDefaultWindowSize();

    private boolean isInFullscreenMode;

    private BufferedImage rawImage;

    private BufferedImage scaledImage;

    public OmniPanel() {
        setOpaque(true);
        setVisible(true);
    }

    BufferedImage getBlankScaledImage() {
        return new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    @Override
    protected void paintComponent(Graphics g) {//updates the displayed image.
        super.paintComponent(g);
        if (scaledImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(scaledImage, 0, 0, null);
        }
    }

    private void rescaleImage() {
        scaledImage = getBlankScaledImage();
        if (rawImage != null)
            scaledImage = ImageScaler.resize(rawImage, scaledImage);
    }

    void setFullscreenMode(boolean fullscreenMode) {
        isInFullscreenMode = fullscreenMode;
        Dimension d = (isInFullscreenMode ? ScreenTools.getMonitorDimension() : windowedDimension);
        if (d.height < 1) d.height = 1;
        if (d.width < 1) d.width = 1;
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
        setSize(d);
        setImage(
                new BufferedImage(
                        getWidth(),
                        getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                )
        );
    }

    public void setImage(BufferedImage bufferedImage) {
        rawImage = bufferedImage;
        rescaleImage();
    }

    /**
     * Re-scale the source image whenever it is resized.
     */
    @Override
    public void setSize(Dimension d) {
        if (d.height <= 0 || d.width <= 0)
            return;
        if (!isInFullscreenMode) {
            windowedDimension = d;
        }
        super.setSize(d);
        rescaleImage();
        repaint();
    }

}
