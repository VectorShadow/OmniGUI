package window;

import image.ImageScaler;

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

    void setFullscreenMode(boolean fullscreenMode) {
        isInFullscreenMode = fullscreenMode;
        Dimension d = (isInFullscreenMode ? ScreenTools.getMonitorDimension() : windowedDimension);
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
        scaledImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        if (rawImage != null)
            scaledImage = ImageScaler.resize(rawImage, scaledImage);
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