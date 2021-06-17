package vsdl.omnigui.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.EventListener;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuiTest {

    private final Dimension D = new Dimension(16, 9);
    private Gui g;

    @BeforeEach
    public void setUp() {
        g = new Gui(D);
    }

    @Test
    public void testAddEventListenerBeforeFrameCreation() {
        assertThrows(IllegalStateException.class, () -> {g.addEventListener(new EventListener() {});});
    }

    @Test
    public void testAddUnsupportedEventListener() {
        g.createFrame(false, null, null);
        assertThrows(IllegalArgumentException.class, () -> {g.addEventListener(new EventListener() {});});
        g.close();
    }
}
