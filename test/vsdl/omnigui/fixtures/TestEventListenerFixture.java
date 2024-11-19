package vsdl.omnigui.fixtures;

import vsdl.omnigui.api.listeners.BoundKeyListener;
import vsdl.omnigui.api.listeners.BoundMouseListener;
import vsdl.omnigui.api.listeners.BoundWindowListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class TestEventListenerFixture {
    public static BoundKeyListener getFullscreenToggleKeyListener() {
        return new BoundKeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER && (e.getModifiersEx() == KeyEvent.ALT_DOWN_MASK || e.getModifiersEx() == KeyEvent.ALT_DOWN_MASK + KeyEvent.ALT_GRAPH_DOWN_MASK)) {
                    System.out.println("ALT ENTER");
                    getBoundFrame().toggleFullScreenMode();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }


    public static BoundMouseListener getCoordinateLocatorMouseListener() {
        return new BoundMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = getBoundGui().getMouseEventLocationOnCanvas(e);
                System.out.println("Mouse clicked at Point(" + p.x + ", " + p.y + ") on canvas.");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public static BoundWindowListener getDefaultCloseWindowListener() {
        return new BoundWindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };
    }
}
