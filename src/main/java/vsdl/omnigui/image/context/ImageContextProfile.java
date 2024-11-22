package vsdl.omnigui.image.context;

import vsdl.omnigui.api.Gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class ImageContextProfile {
    private final ImageContextHierarchy hierarchy;
    private final HashMap<Integer, ImageContext> hotkeys;

    private boolean isActive = true;

    private int rgbIgnore = 0xff000000;

    ImageContextProfile() {
        hierarchy = new ImageContextHierarchy();
        hotkeys = new HashMap<>();
    }

    void appendImageContext(ImageContext imageContext) {
        hierarchy.append(imageContext);
    }

    void appendImageContext(
            ImageContext imageContext,
            int... extendedKeyCodeAndModifiers
    ) {
        appendImageContext(imageContext);
        for (int extendedKeyCodeAndModifier : extendedKeyCodeAndModifiers) {
            hotkeys.put(extendedKeyCodeAndModifier, imageContext);
        }
    }

    void setRgbIgnore(int rgbIgnore){
        this.rgbIgnore = rgbIgnore;
    }

    public void paint(Gui g) {
        for (ImageContext c : hierarchy) {
            g.paintCanvas(c.getSource().image(), c.getOrigin(), rgbIgnore);
        }
    }

    public void keyPress(KeyEvent e) {
        ImageContext context = hotkeys.get(e.getExtendedKeyCode() | e.getModifiersEx());
        if (context != null) {
            context.getSource().input(e);
        } else {
            //attempt to apply the keypress to the uppermost context in the hierarchy
            //this way contexts with a large number of valid inputs need not hash them all as hotkeys.
            hierarchy.getUppermost().getSource().input(e);
        }
    }

    public void mouseClick(Point p, boolean isLeftClick) {
        ImageContext context = hierarchy.find(p);
        if (context != null) {
            Point imageCoordinates = new Point(p.x - context.getOrigin().x, p.y - context.getOrigin().y);
            if (isLeftClick) {
                context.getSource().invoke(imageCoordinates);
            } else {
                context.getSource().inform(imageCoordinates);
            }
        }
    }

    public void startAutoRepaintDaemon(Gui gui, int repaintInterval) {
        isActive = true;
        new Thread(() -> {
            do {
                try {
                    Thread.sleep(Math.max(16, repaintInterval));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                gui.clearCanvas();
                paint(gui);
                gui.updateFrameImage();
            } while (isActive);
        }).start();
    }

    public void stopAutoRepaintDaemon() {
        isActive = false;
    }

    public ImageContextHierarchy getHierarchy() {
        return hierarchy;
    }

    public HashMap<Integer, ImageContext> getHotkeys() {
        return hotkeys;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getRgbIgnore() {
        return rgbIgnore;
    }
}