package vsdl.omnigui.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface InteractiveImageSource {
    //return the image associated with this Interactive Image Source
    BufferedImage image();
    //provide information associated with this Interactive Image Source
    void inform();
    //invoke the action associated with this Interactive Image Source
    void invoke();
    //return the size of this Interactive Image Source
    Dimension size();
}
