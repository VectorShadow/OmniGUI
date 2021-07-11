package vsdl.omnigui.image.source;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public interface InteractiveImageSource {
    //return the image associated with this Interactive Image Source
    BufferedImage image();
    //provide information associated with this Interactive Image Source
    void inform(Point imageCoordinates);
    //handle input for the provided key event for this Interactive Image Source
    void input(KeyEvent e);
    //invoke the action associated with this Interactive Image Source
    void invoke(Point imageCoordinates);
    //return the size of this Interactive Image Source
    Dimension size();
}
