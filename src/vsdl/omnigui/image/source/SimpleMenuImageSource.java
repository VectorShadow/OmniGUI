package vsdl.omnigui.image.source;

import org.jetbrains.annotations.NotNull;
import vsdl.omnigui.image.ImageComposer;
import vsdl.omnigui.image.TextImager;

import java.awt.*;
import static java.awt.event.KeyEvent.*;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class SimpleMenuImageSource implements InteractiveImageSource {

    private static final int MENU_BACKGROUND_COLOR = 0;
    private static final int MENU_TITLE_COLOR = 1;
    private static final int MENU_SELECTED_BACKGROUND = 2;
    private static final int MENU_SELECTED_FOREGROUND = 3;
    private static final int MENU_ENABLED_COLOR = 4;
    private static final int MENU_DISABLED_COLOR = 5;
    private static final int COLOR_SIZE = 6;

    private final int OPTION_COUNT;
    private final String TITLE;
    private final Color[] COLORS = new Color[COLOR_SIZE];
    private final String[] OPTIONS;
    private final String[] INFORMATIONS;
    private final boolean[] OPTION_ENABLEMENTS;
    private final Runnable[] INVOCATIONS;
    private final Runnable ESCAPE_INVOCATION;

    private final int LINE_HEIGHT;
    private final int IMAGE_HEIGHT;
    private final int IMAGE_WIDTH;

    private int menuIndex = -1;

    public SimpleMenuImageSource(
            String menuTitle,
            String[] options,
            String[] optionDescriptions,
            boolean[] optionDefaultEnabledStates,
            Runnable[] optionExecutions,
            Runnable exitExecution,
            int lineHeightInPixels,
            Color menuBackgroundColor,
            Color menuTitleColor,
            Color selectedOptionBackgroundColor,
            Color selectedOptionForegroundColor,
            Color enabledOptionColor,
            Color disabledOptionColor
    ){
        OPTION_COUNT = options.length;
        if (
                optionDescriptions.length != OPTION_COUNT ||
                        optionDefaultEnabledStates.length != OPTION_COUNT ||
                        optionExecutions.length != OPTION_COUNT
        ) {
            throw new IllegalArgumentException("Number of options, option descriptions, option enabled states, and option executions must be the same.");
        }
        TITLE = menuTitle;
        OPTIONS = options;
        INFORMATIONS = optionDescriptions;
        OPTION_ENABLEMENTS = optionDefaultEnabledStates;
        INVOCATIONS = optionExecutions;
        ESCAPE_INVOCATION = exitExecution;
        LINE_HEIGHT = lineHeightInPixels;
        COLORS[MENU_BACKGROUND_COLOR] = menuBackgroundColor;
        COLORS[MENU_TITLE_COLOR] = menuTitleColor;
        COLORS[MENU_SELECTED_BACKGROUND] = selectedOptionBackgroundColor;
        COLORS[MENU_SELECTED_FOREGROUND] = selectedOptionForegroundColor;
        COLORS[MENU_ENABLED_COLOR] = enabledOptionColor;
        COLORS[MENU_DISABLED_COLOR] = disabledOptionColor;
        IMAGE_HEIGHT = LINE_HEIGHT * (OPTION_COUNT + 2); //title, empty line, one line per option
        int maxWidth = TextImager.measureText(TITLE, LINE_HEIGHT).width;
        for (int i = 0; i < OPTION_COUNT; ++i) {
            if (OPTION_ENABLEMENTS[i] && menuIndex < 0) {
                menuIndex = i;
            }
            Dimension d = TextImager.measureText(OPTIONS[i], LINE_HEIGHT);
            if (d.width > maxWidth) {
                maxWidth = d.width;
            }
        }
        IMAGE_WIDTH = maxWidth;
        if (menuIndex < 0) {
            throw new IllegalArgumentException("At least one menu option must be enabled.");
        }
    }

    private int getOptionIndex(int clickHeight) {
        return (clickHeight / LINE_HEIGHT) - 2;
    }

    public Point getOrigin(Dimension canvasSize) {
        int x = canvasSize.width - IMAGE_WIDTH;
        int y = canvasSize.height - IMAGE_HEIGHT;
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Image size exceeds canvas size.");
        return new Point(x / 2, y / 2);
    }

    @Override
    public BufferedImage image() {
        Dimension d = size();
        BufferedImage result = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        int width = TextImager.measureText(TITLE, LINE_HEIGHT).width;
        int row = 0;
        ImageComposer.superimpose(
                TextImager.image(
                        TITLE,
                        LINE_HEIGHT,
                        LINE_HEIGHT,
                        width,
                        COLORS[MENU_TITLE_COLOR],
                        COLORS[MENU_BACKGROUND_COLOR]
                ),
                result,
                new Point((IMAGE_WIDTH - width) / 2, row)
        );
        row += 2;
        for (int i = 0; i < OPTION_COUNT; ++i) {
            width = TextImager.measureText(OPTIONS[i], LINE_HEIGHT).width;
            ImageComposer.superimpose(
                    TextImager.image(
                            OPTIONS[i],
                            LINE_HEIGHT,
                            LINE_HEIGHT,
                            width,
                            COLORS[
                                    OPTION_ENABLEMENTS[i]
                                            ? menuIndex == i
                                            ? MENU_SELECTED_FOREGROUND
                                            : MENU_ENABLED_COLOR
                                            : MENU_DISABLED_COLOR
                                    ],
                            COLORS[
                                    OPTION_ENABLEMENTS[i] && menuIndex == i
                                            ? MENU_SELECTED_BACKGROUND
                                            : MENU_BACKGROUND_COLOR
                                    ]
                    ),
                    result,
                    new Point((IMAGE_WIDTH - width) / 2, row++ * LINE_HEIGHT)
            );
        }
        return result;
    }

    @Override
    public void inform(@NotNull Point imageCoordinates) {
        //todo
    }

    @Override
    public void input(KeyEvent e) {
        switch (e.getExtendedKeyCode()) {
            case VK_ENTER:
                INVOCATIONS[menuIndex].run();
                break;
            case VK_UP: case VK_NUMPAD8:
                do {
                    if (--menuIndex < 0) menuIndex = OPTION_COUNT - 1;
                } while(!OPTION_ENABLEMENTS[menuIndex]);
                break;
            case VK_DOWN: case VK_NUMPAD2:
                do {
                    if (++menuIndex >= OPTION_COUNT) menuIndex = 0;
                } while(!OPTION_ENABLEMENTS[menuIndex]);
                break;
            case VK_ESCAPE:
                ESCAPE_INVOCATION.run();
                break;
        }
    }

    @Override
    public void invoke(Point imageCoordinates) {
        int optionIndex = getOptionIndex(imageCoordinates == null ? 0 : imageCoordinates.y);
        if (optionIndex == menuIndex) {
            INVOCATIONS[menuIndex].run();
        } else if (optionIndex >= 0 && OPTION_ENABLEMENTS[optionIndex]) {
            menuIndex = optionIndex;
        }
    }

    @Override
    public Dimension size() {
        return new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT);
    }
}
