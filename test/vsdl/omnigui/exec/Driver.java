package vsdl.omnigui.exec;

import vsdl.omnigui.api.Gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Driver {
    public static void main(String[] args) {
        Gui gui = new Gui(new Dimension(160, 90));
        gui.createFrame(false, "Test", null);
        BufferedImage testImage = new BufferedImage(160, 90, BufferedImage.TYPE_INT_RGB);
        Point origin = new Point(0,0);
        for (int i = 0; i < 160; ++i) {
            for (int j = 0; j < 90; ++j) {
                testImage.setRGB(i, j, (int)(Math.random()*0x00ffffff));
            }
        }
        BufferedImage transparentTestImage = new BufferedImage(80, 45, BufferedImage.TYPE_INT_RGB);
        Point xparentOrigin = new Point(40, 22);
        int RGB = 0xffff0000;
        for (int i = 0; i < 80; ++i) {
            for (int j = 0; j < 45; ++j) {
                if (i < 2 || i > 77 || j < 2 || j > 42)
                    transparentTestImage.setRGB(i, j, 0x00000000);
                else
                    transparentTestImage.setRGB(i, j, RGB);
            }
        }
        gui.paintCanvas(testImage, origin, -1);
        gui.paintCanvas(transparentTestImage, xparentOrigin, RGB);
        gui.updateFrameImage();
        gui.addEventListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Point p = gui.getMouseEventLocationOnCanvas(e);
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
                }
        );
    }
}
