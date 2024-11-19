package vsdl.omnigui.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vsdl.omnigui.fixtures.TestEventListenerFixture;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GuiTest {

    private final Dimension D = new Dimension(16, 9);
    private Gui g;

    @BeforeEach
    public void setUp() {
        g = new Gui(D);
    }

    @Test
    public void testAddEventListenerBeforeFrameCreation() {
        assertThrows(IllegalStateException.class, () -> g.addEventListener(new BoundEventListener() {}));
    }

    @Test
    public void testAddUnsupportedEventListener() {
        g.createFrame(false, null, null);
        assertThrows(IllegalArgumentException.class, () -> g.addEventListener(new BoundEventListener() {}));
        g.close();
    }

    @Test
    public void testAddSupportedEventListeners() {
        BoundEventListener k = TestEventListenerFixture.getFullscreenToggleKeyListener();
        BoundEventListener m = TestEventListenerFixture.getCoordinateLocatorMouseListener();
        BoundEventListener w = TestEventListenerFixture.getDefaultCloseWindowListener();
        g.createFrame(false, null, null);
        g.addEventListener(k);
        g.addEventListener(m);
        assertEquals(g, k.getBoundGui());
        assertEquals(g.getFrame(), m.getBoundFrame());
        assertNull(w.getBoundGui());
        assertNull(w.getBoundFrame());
    }
}
