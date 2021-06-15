package window;

import java.awt.*;

public class ScreenTools {
    public static Dimension getMonitorDimension() {
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        return new Dimension(dm.getWidth(), dm.getHeight());
    }
    public static Dimension getDefaultWindowSize() {
        return new Dimension(
                (int)(getMonitorDimension().width / Math.sqrt(2.0)),
                (int)(getMonitorDimension().height / Math.sqrt(2.0))
        );
    }

    public static Point centeredOrigin(Dimension frameSize) {
        Dimension monitorDimension = getMonitorDimension();
        return new Point(
                ((int)(monitorDimension.getWidth() - frameSize.getWidth())) / 2,
                ((int)(monitorDimension.getHeight() - frameSize.getHeight())) / 2
        );
    }
}
