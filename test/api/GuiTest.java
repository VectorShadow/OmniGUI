package api;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.EventListener;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuiTest {

    private static final Dimension D = new Dimension(16, 9);

    @Test
    public void testEventListenerAddedBeforeFrameCreation() {
        Gui g = new Gui(D);
        assertThrows(IllegalStateException.class, () -> {g.addEventListener(new EventListener() {});});
    }

    @Test
    public void testAddUnsupportedEventListener() {
        Gui g = new Gui(D);
        g.createFrame(false, null, null);
        assertThrows(IllegalArgumentException.class, () -> {g.addEventListener(new EventListener() {});});
        g.close();
    }
}
