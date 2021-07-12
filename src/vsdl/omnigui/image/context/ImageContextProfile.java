package vsdl.omnigui.image.context;

import vsdl.omnigui.api.Gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class ImageContextProfile {
    final ImageContextHierarchy ICH;
    final HashMap<Integer, ImageContext> HOTKEYS;
    int rgbIgnore = 0xff000000;

    ImageContextProfile() {
        ICH = new ImageContextHierarchy();
        HOTKEYS = new HashMap<>();
    }

    public void paint(Gui g) {
        for (ImageContext c : ICH) {
            g.paintCanvas(c.getSource().image(), c.getOrigin(), rgbIgnore);
        }
    }

    public void keyPress(KeyEvent e) {
        ImageContext context = HOTKEYS.get(e.getExtendedKeyCode() | e.getModifiersEx());
        if (context != null) context.getSource().input(e);
    }

    public void mouseClick(Point p, boolean isLeftClick) {
        ImageContext context = ICH.find(p);
        if (context != null) {
            Point imageCoordinates = new Point(p.x - context.getOrigin().x, p.y - context.getOrigin().y);
            if (isLeftClick) {
                context.getSource().invoke(imageCoordinates);
            } else {
                context.getSource().inform(imageCoordinates);
            }
        }
    }
}
