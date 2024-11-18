package vsdl.omnigui.image.source;

import org.jetbrains.annotations.NotNull;
import vsdl.omnigui.image.ImageComposer;
import vsdl.omnigui.image.TextImager;

import java.awt.*;
import static java.awt.event.KeyEvent.*;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ScrollableMenuTextDialogImageSource extends InteractiveTextDialogImageSource {

    public static final class ScrollableMenuTextDialogImageSourceBuilder
            extends InteractiveTextDialogImageSourceBuilder {

        private ScrollableMenuTextDialogImageSourceBuilder() {
            SRC = new ScrollableMenuTextDialogImageSource();
        }

        @Override
        Dimension calculateImageDimension() {
            final int H = SRC.textHeight * (SRC.optionCount + 2); //title, empty line, one line per option
            int maxWidth = TextImager.measureText(SRC.title, SRC.textHeight).width;
            for (int i = 0; i < SRC.optionCount; ++i) {
                Dimension d = TextImager.measureText(SRC.optionNames[i], SRC.textHeight);
                if (d.width > maxWidth) {
                    maxWidth = d.width;
                }
            }
            return new Dimension(maxWidth, H);
        }

        @Override
        public ScrollableMenuTextDialogImageSource build() {
            boolean atLeastOneOptionEnabled = false;
            for (int i = 0; i < SRC.optionCount; ++i) {
                if (SRC.optionEnabledStates[i]) {
                    atLeastOneOptionEnabled = true;
                    if (!SRC.optionEnabledStates[SRC.selectedOption]) {
                        SRC.selectedOption = i; //require selected option to also be enabled
                    }
                    break;
                }
            }
            if (!atLeastOneOptionEnabled) {
                throw new IllegalStateException("At least one menu option must be enabled.");
            }
            if (SRC.optionNames == null) {
                throw new IllegalArgumentException("Number of options must be > 0.");
            }
            return (ScrollableMenuTextDialogImageSource) super.build();
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setTitle(String title) {
            return (ScrollableMenuTextDialogImageSourceBuilder) super.setTitle(title);
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setTextHeight(int textHeight) {
            return (ScrollableMenuTextDialogImageSourceBuilder) super.setTextHeight(textHeight);
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setOptionCount(int optionCount) {
            return (ScrollableMenuTextDialogImageSourceBuilder) super.setOptionCount(optionCount);
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setOptionCount(
                int optionCount,
                int defaultSelectedOptionIndex
        ) {
            return (ScrollableMenuTextDialogImageSourceBuilder)
                    super.setOptionCount(optionCount, defaultSelectedOptionIndex);
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setOptionNames(String... optionNames) {
            return (ScrollableMenuTextDialogImageSourceBuilder) super.setOptionNames(optionNames);
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setOptionEnabledState(boolean enabled, int atIndex) {
            return (ScrollableMenuTextDialogImageSourceBuilder) super.setOptionEnabledState(enabled, atIndex);
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setColors(Color... colors) {
            return (ScrollableMenuTextDialogImageSourceBuilder) super.setColors(colors);
        }

        @Override
        public ScrollableMenuTextDialogImageSourceBuilder setEscapeExecution(Runnable r) {
            return (ScrollableMenuTextDialogImageSourceBuilder) super.setEscapeExecution(r);
        }

        public ScrollableMenuTextDialogImageSourceBuilder setBackgroundColor(Color color) {
            SRC.colors[BG_COLOR] = color;
            return this;
        }

        public ScrollableMenuTextDialogImageSourceBuilder setTitleColor(Color color) {
            SRC.colors[TITLE_COLOR] = color;
            return this;
        }

        public ScrollableMenuTextDialogImageSourceBuilder setDisabledColors(Color fg, Color bg) {
            SRC.colors[DISABLED_OPTION_PRIMARY_COLOR] = fg;
            SRC.colors[DISABLED_OPTION_SECONDARY_COLOR] = bg;
            return this;
        }

        public ScrollableMenuTextDialogImageSourceBuilder setEnabledColors(Color fg, Color bg) {
            SRC.colors[ENABLED_OPTION_PRIMARY_COLOR] = fg;
            SRC.colors[ENABLED_OPTION_SECONDARY_COLOR] = bg;
            return this;
        }

        public ScrollableMenuTextDialogImageSourceBuilder setSelectedColors(Color fg, Color bg) {
            SRC.colors[SELECTED_OPTION_PRIMARY_COLOR] = fg;
            SRC.colors[SELECTED_OPTION_SECONDARY_COLOR] = bg;
            return this;
        }

        public ScrollableMenuTextDialogImageSourceBuilder setOptionDescriptions(String... optionDescriptions) {
            if (optionDescriptions.length != SRC.optionCount) {
                throw new IllegalArgumentException("Number of option descriptions must match number of options.");
            }
            ((ScrollableMenuTextDialogImageSource)SRC).optionDescriptions = optionDescriptions;
            return this;
        }

        public ScrollableMenuTextDialogImageSourceBuilder setOptionExecutions(Runnable... optionExecutions) {
            if (optionExecutions.length != SRC.optionCount) {
                throw new IllegalArgumentException("Number of option descriptions must match number of options.");
            }
            ((ScrollableMenuTextDialogImageSource)SRC).optionExecutions = optionExecutions;
            return this;
        }
    }

    private String[] optionDescriptions;
    private Runnable[] optionExecutions;

    private ScrollableMenuTextDialogImageSource(){}

    @Override
    public BufferedImage image() {
        Dimension d = size();
        BufferedImage result = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        int width = TextImager.measureText(title, textHeight).width;
        int row = 0;
        ImageComposer.superimpose(
                TextImager.image(
                        title,
                        textHeight,
                        textHeight,
                        width,
                        colors[TITLE_COLOR],
                        colors[BG_COLOR]
                ),
                result,
                new Point((imageDimension.width - width) / 2, row)
        );
        row += 2;
        for (int i = 0; i < optionCount; ++i) {
            width = TextImager.measureText(optionNames[i], textHeight).width;
            ImageComposer.superimpose(
                    TextImager.image(
                            optionNames[i],
                            textHeight,
                            textHeight,
                            width,
                            colors[
                                    optionEnabledStates[i]
                                            ? selectedOption == i
                                            ? SELECTED_OPTION_PRIMARY_COLOR
                                            : ENABLED_OPTION_PRIMARY_COLOR
                                            : DISABLED_OPTION_PRIMARY_COLOR
                                    ],
                            colors[
                                    optionEnabledStates[i] && selectedOption == i
                                            ? SELECTED_OPTION_SECONDARY_COLOR
                                            : BG_COLOR
                                    ]
                    ),
                    result,
                    new Point((imageDimension.width - width) / 2, row++ * textHeight)
            );
        }
        return result;
    }

    @Override
    public void inform(@NotNull Point imageCoordinates) {
        //todo?
    }

    @Override
    public void input(KeyEvent e) {
        switch (e.getExtendedKeyCode()) {
            case VK_ENTER:
                optionExecutions[selectedOption].run();
                break;
            case VK_UP: case VK_NUMPAD8:
                do {
                    if (--selectedOption < 0) selectedOption = optionCount - 1;
                } while(!optionEnabledStates[selectedOption]);
                break;
            case VK_DOWN: case VK_NUMPAD2:
                do {
                    if (++selectedOption >= optionCount) selectedOption = 0;
                } while(!optionEnabledStates[selectedOption]);
                break;
            case VK_ESCAPE:
                escape();
                break;
        }
    }

    @Override
    public void invoke(Point imageCoordinates) {
        if (imageCoordinates == null) return;
        int optionIndex = getOptionIndex(imageCoordinates.y);
        if (optionIndex == selectedOption) {
            optionExecutions[selectedOption].run();
        } else if (optionIndex >= 0 && optionEnabledStates[optionIndex]) {
            selectedOption = optionIndex;
        }
    }

    public static ScrollableMenuTextDialogImageSourceBuilder builder() {
        return new ScrollableMenuTextDialogImageSourceBuilder();
    }
}
