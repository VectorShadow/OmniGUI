package vsdl.omnigui.window;

import org.junit.jupiter.api.Test;

import java.awt.*;

public class ScreenToolsTest {

    @Test
    public void testGetDefaultWindowSize() {
        Dimension monitorDim = ScreenTools.getMonitorDimension();
        Dimension defaultWindowDim = ScreenTools.getDefaultWindowSize();
        assert defaultWindowDim.height == (int)(monitorDim.height / Math.sqrt(2.0))
                && defaultWindowDim.width == (int)(monitorDim.width / Math.sqrt(2.0));
    }

    @Test
    public void testCenteredOrigin() {
        Dimension monitorDim = ScreenTools.getMonitorDimension();
        Dimension defaultWindowDim = ScreenTools.getDefaultWindowSize();
        Point frameOrigin = ScreenTools.centeredOrigin(defaultWindowDim);
        assert frameOrigin.x == (int)(monitorDim.getWidth() - defaultWindowDim.getWidth()) / 2
                && frameOrigin.y == (int)(monitorDim.getHeight() - defaultWindowDim.getHeight()) / 2;
    }
}
