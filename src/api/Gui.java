package api;

import canvas.Canvas;
import window.OmniFrame;

import static image.ImageScaler.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.EventListener;

public class Gui {

    private final Canvas canvas;

    private OmniFrame frame = null;

    public Gui(Dimension canvasDimension) {
        canvas = new Canvas(canvasDimension.height, canvasDimension.width);
    }

    /**
     * Add a new EventListener to an existing frame.
     * KeyListener, MouseListener, and WindowListener are currently supported.
     * @throws IllegalStateException if a frame does not exist
     * @throws IllegalArgumentException if the EventListener argument is of an unsupported class
     */
    public void addEventListener(EventListener listener) {
        if (frame == null) {
            throw new IllegalStateException("EventListeners may not be added before a frame is created.");
        }
        if (listener instanceof KeyListener) {
            frame.addKeyListener((KeyListener) listener);
        } else if (listener instanceof MouseListener) { //to correctly capture coordinates, this must be on the pane
            frame.getContentPane().addMouseListener((MouseListener) listener);
        } else if (listener instanceof WindowListener) {
            frame.addWindowListener((WindowListener) listener);
        } else {
            throw new IllegalArgumentException("Unsupported EventListener implementation: " + listener.getClass());
        }
    }

    /**
     * Clear the image on the canvas.
     * This should be done either prior to all paintCanvas calls for a specific state, or after the updateFrameImage
     * call for a specific state.
     */
    public void clearCanvas() {
        canvas.clearImage();
    }

    /**
     * Dispose of the current frame. This should be called prior to terminating the program using the Gui.
     */
    public void close() {
        frame.dispose();
    }


    /**
     * Create a new frame for displaying the canvas image.
     * @param fullScreenMode true to create the frame in full screen mode, false to create it in windowed mode
     * @param frameTitle the title to display at the top of the frame in windowed mode
     * @param iconImagePath the icon image for the frame
     */
    public void createFrame(boolean fullScreenMode, String frameTitle, String iconImagePath) {
        frame = new OmniFrame(fullScreenMode);
        if (frameTitle != null) {
            frame.setTitle(frameTitle);
        }
        if (iconImagePath != null) {
            frame.setIconImage(new ImageIcon(iconImagePath).getImage());
        }
    }

    /**
     * Get the canvas coordinates of a MouseEvent on the frame image.
     * @param mouseEvent the MouseEvent to process
     * @return a Point on the canvas
     */
    public Point getMouseEventLocationOnCanvas(MouseEvent mouseEvent) {
        Point eventLocation = new Point(mouseEvent.getX(), mouseEvent.getY());
        return descalePixelCoordinate(frame.getBlankPanelImage(), canvas.getImage(), eventLocation);
    }

    /**
     * Update the image on the canvas by painting a new image on top of it.
     * @param image the new image to paint onto the canvas
     * @param imageOrigin the upper left corner of the new image as a canvas coordinate
     * @param rgbIgnore indicates that any pixels with this rgb value will not be overwritten,
     *                  to support transparency effects
     */
    public void paintCanvas(BufferedImage image, Point imageOrigin, int rgbIgnore) {
        canvas.updateImage(image, imageOrigin, rgbIgnore);
    }

    /**
     * Update the frame's panel image with the current canvas image.
     */
    public void updateFrameImage() {
        frame.updatePanelImage(canvas.getImage());
    }

    /**
     * Toggle between fullscreen and windowed mode.
     */
    public void toggleFullscreenMode() {
        frame.toggleFullScreenMode();
    }

}
