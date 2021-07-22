package vsdl.omnigui.image.source;

import java.awt.*;

public abstract class InteractiveTextDialogImageSource implements InteractiveImageSource {
    protected static final int BG_COLOR = 0;
    protected static final int TITLE_COLOR = 1;
    protected static final int ENABLED_OPTION_PRIMARY_COLOR = 2;
    protected static final int SELECTED_OPTION_PRIMARY_COLOR = 3;
    protected static final int DISABLED_OPTION_PRIMARY_COLOR = 4;
    protected static final int ENABLED_OPTION_SECONDARY_COLOR = 5;
    protected static final int SELECTED_OPTION_SECONDARY_COLOR = 6;
    protected static final int DISABLED_OPTION_SECONDARY_COLOR = 7;
    protected static final int COLOR_COUNT = 8;

    private static final int DEFAULT_TEXT_HEIGHT = 16;
    private static final Color[] DEFAULT_COLORS = {
        Color.BLACK,
                Color.WHITE,
                Color.LIGHT_GRAY,
                Color.WHITE,
                Color.DARK_GRAY,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK
    };

    //default colors
    protected Color[] colors;

    protected String title;
    protected int textHeight;
    protected int optionCount;
    protected int selectedOption;

    protected String[] optionNames;
    protected boolean[] optionEnabledStates;

    protected Runnable escapeExecution;

    protected Dimension imageDimension;

    protected InteractiveTextDialogImageSource() {
        title = "";
        textHeight = DEFAULT_TEXT_HEIGHT;
        optionCount = 0;
        selectedOption = -1;
        colors = DEFAULT_COLORS;
        escapeExecution = () -> {};
    }

    public void escape() {
        escapeExecution.run();
    }

    @Override
    public Dimension size() {
        return imageDimension;
    }
}
