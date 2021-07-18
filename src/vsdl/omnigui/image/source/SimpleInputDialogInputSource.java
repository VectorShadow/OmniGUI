package vsdl.omnigui.image.source;

import vsdl.omnigui.image.TextImager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class SimpleInputDialogInputSource implements InteractiveImageSource {

    private static final int BACKGROUND_COLOR = 0;
    private static final int TITLE_COLOR = 1;
    private static final int FIELD_NAME_COLOR = 2;
    private static final int SELECTED_FIELD_NAME_COLOR = 3;
    private static final int FIELD_BACKGROUND_COLOR = 4;
    private static final int FIELD_FOREGROUND_COLOR = 5;
    private static final int COLOR_SIZE = 6;

    private final int FIELD_COUNT;
    private final String TITLE;
    private final String[] INPUT_FIELDS;
    private final String[] INPUT_FIELD_NAMES;
    private final int[] INPUT_SIZE_LIMITS;
    private final boolean[] INPUT_FIELD_MASKS;
    private final Color[] COLORS = new Color[COLOR_SIZE];
    private final Runnable ESCAPE_INVOCATION;

    private final int LINE_HEIGHT;
    private final int IMAGE_HEIGHT;
    private final int IMAGE_WIDTH;

    private int selectedField = 0;

    public SimpleInputDialogInputSource(
        String title,
        String[] inputFieldNames,
        int[] inputSizeLimits,
        boolean[] inputFieldMasks,
        Runnable escapeInvocation,
        int lineHeight,
        Color backgroundColor,
        Color titleColor,
        Color fieldNameColor,
        Color selectedFieldNameColor,
        Color fieldBackgroundColor,
        Color fieldForegroundColor
    ){
        if (inputFieldNames == null || inputFieldNames.length == 0) {
            throw new IllegalArgumentException("Number of input fields must be > 0.");
        }
        FIELD_COUNT = inputFieldNames.length;
        if (
                inputSizeLimits.length != FIELD_COUNT
                        || inputFieldMasks.length != FIELD_COUNT
        ) {
            throw new IllegalArgumentException(
                    "Number of field names, size limits, and masks must be the same."
            );
        }
        TITLE = title;
        INPUT_FIELDS = new String[FIELD_COUNT];
        for (int i = 0; i < FIELD_COUNT; ++i) {
            INPUT_FIELDS[i] = "";
        }
        INPUT_FIELD_NAMES = inputFieldNames;
        INPUT_SIZE_LIMITS = inputSizeLimits;
        INPUT_FIELD_MASKS = inputFieldMasks;

        COLORS[BACKGROUND_COLOR] = backgroundColor;
        COLORS[TITLE_COLOR] = titleColor;
        COLORS[FIELD_NAME_COLOR] = fieldNameColor;
        COLORS[SELECTED_FIELD_NAME_COLOR] = selectedFieldNameColor;
        COLORS[FIELD_BACKGROUND_COLOR] = fieldBackgroundColor;
        COLORS[FIELD_FOREGROUND_COLOR] = fieldForegroundColor;
        ESCAPE_INVOCATION = escapeInvocation;
        LINE_HEIGHT = lineHeight;
        IMAGE_HEIGHT = LINE_HEIGHT * (FIELD_COUNT + 2); //title, empty line, one line per field
        int maxWidth = TextImager.measureText(TITLE, LINE_HEIGHT).width;
        for (int i = 0; i < FIELD_COUNT; ++i) {
            Dimension dName = TextImager.measureText(INPUT_FIELDS[i], LINE_HEIGHT);
            Dimension dField = TextImager.measureEmptyField(INPUT_SIZE_LIMITS[i], LINE_HEIGHT);
            int width = dName.width + dField.width;
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        IMAGE_WIDTH = maxWidth;
    }

    @Override
    public BufferedImage image() {
        return null;
    }

    @Override
    public void inform(Point imageCoordinates) {

    }

    @Override
    public void input(KeyEvent e) {

    }

    @Override
    public void invoke(Point imageCoordinates) {

    }

    @Override
    public Dimension size() {
        return null;
    }
}
