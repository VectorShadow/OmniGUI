package vsdl.omnigui.canvas;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class CanvasTest {

    @Test
    public void testInvalidCanvasDimensions() {
        assertThrows(IllegalArgumentException.class, () -> {
            vsdl.omnigui.canvas.Canvas c = new vsdl.omnigui.canvas.Canvas(0, 1);});
        assertThrows(IllegalArgumentException.class, () -> {
            vsdl.omnigui.canvas.Canvas c = new vsdl.omnigui.canvas.Canvas(1, 0);});
        assertThrows(IllegalArgumentException.class, () -> {
            vsdl.omnigui.canvas.Canvas c = new vsdl.omnigui.canvas.Canvas(-1, 1);});
        assertThrows(IllegalArgumentException.class, () -> {
            vsdl.omnigui.canvas.Canvas c = new vsdl.omnigui.canvas.Canvas(1, -1);});
        assertThrows(IllegalArgumentException.class, () -> {
            vsdl.omnigui.canvas.Canvas c = new vsdl.omnigui.canvas.Canvas(0, 0);});
        assertThrows(IllegalArgumentException.class, () -> {
            vsdl.omnigui.canvas.Canvas c = new vsdl.omnigui.canvas.Canvas(-1, -1);});
    }

    @Test
    public void testCanvasOriginOutOfBounds() {
        vsdl.omnigui.canvas.Canvas c = new Canvas(1,1);
        BufferedImage b = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        Point p = new Point(1,1);
        assertThrows(IllegalArgumentException.class, () -> {c.updateImage(b, p, 0);});
    }
}
