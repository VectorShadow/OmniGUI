package vsdl.omnigui.window;

import vsdl.omnigui.image.ImageScaler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static vsdl.omnigui.image.ImageComposer.composeBlankImage;

public class OmniPanel extends JPanel {

    private Dimension windowedDimension = ScreenTools.getDefaultWindowSize();

    private boolean isInFullscreenMode;

    private BufferedImage rawImage;

    private BufferedImage scaledImage;

    public OmniPanel(boolean isInFullscreenMode) {
        this.isInFullscreenMode = isInFullscreenMode;
        setOpaque(true);
        setVisible(true);
    }

    private Dimension getImageDimension() {
        return (isInFullscreenMode ? ScreenTools.getMonitorDimension() : windowedDimension);
    }

    BufferedImage getBlankScaledImage() {
        return composeBlankImage(
                getImageDimension().width,
                getImageDimension().height
        );
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
        if (windowedDimension.height < 1) windowedDimension.height = 1;
        if (windowedDimension.width < 1) windowedDimension.width = 1;
        setPreferredSize(windowedDimension);
        setMinimumSize(windowedDimension);
        setMaximumSize(windowedDimension);
        setSize(windowedDimension);
        rescaleImage();
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
        windowedDimension = d;
        super.setSize(d);
        rescaleImage();
        repaint();
    }

}
