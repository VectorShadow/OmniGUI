package vsdl.omnigui.image.source;

import org.jetbrains.annotations.NotNull;
import vsdl.omnigui.image.ImageComposer;
import vsdl.omnigui.image.InputEventUtils;
import vsdl.omnigui.image.TextImager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class FieldEntryTextDialogImageSource extends InteractiveTextDialogImageSource {

    public static final class FieldEntryTextDialogImageSourceBuilder extends InteractiveTextDialogImageSourceBuilder {

        private FieldEntryTextDialogImageSourceBuilder() {
            SRC = new FieldEntryTextDialogImageSource();
        }

        @Override
        Dimension calculateImageDimension() {
            //title, empty line, one line per field + one pixel per line to show space between fields
            final int H = SRC.textHeight * (SRC.optionCount + 2) + SRC.optionCount;
            int maxWidth = TextImager.measureText(SRC.title, SRC.textHeight).width;
            for (int i = 0; i < SRC.optionCount; ++i) {
                Dimension dName = TextImager.measureText(
                        SRC.optionNames[i],
                        SRC.textHeight
                );
                Dimension dField = TextImager.measureEmptyField(
                        ((FieldEntryTextDialogImageSource)SRC).inputSizeLimits[i],
                        SRC.textHeight
                );
                int width = Math.max(2 * dName.width, 2 * dField.width);
                if (width > maxWidth) {
                    maxWidth = width;
                }
            }
            return new Dimension(maxWidth, H);
        }

        private void initializeFields() {
            if (((FieldEntryTextDialogImageSource) SRC).inputFields == null) {
                ((FieldEntryTextDialogImageSource) SRC).inputFields = new String[SRC.optionCount];
                for (int i = 0; i < SRC.optionCount; ++i) {
                    ((FieldEntryTextDialogImageSource) SRC).inputFields[i] = "";
                }
            }
        }

        @Override
        public FieldEntryTextDialogImageSource build() {
            if (SRC.optionNames == null || SRC.optionCount == 0) {
                throw new IllegalArgumentException("Number of input fields must be > 0.");
            }
            if (((FieldEntryTextDialogImageSource)SRC).inputSizeLimits.length != SRC.optionCount ||
                    SRC.optionEnabledStates.length != SRC.optionCount
            ) {
                throw new IllegalArgumentException(
                        "Number of field names, size limits, and masks must be the same."
                );
            }
            initializeFields();
            return (FieldEntryTextDialogImageSource) super.build();
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setTitle(String title) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setTitle(title);
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setTextHeight(int textHeight) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setTextHeight(textHeight);
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setOptionCount(int optionCount) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setOptionCount(optionCount);
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setOptionCount(int optionCount, int defaultSelectedOptionIndex) {
            return (FieldEntryTextDialogImageSourceBuilder)
                    super.setOptionCount(optionCount, defaultSelectedOptionIndex);
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setOptionNames(String... optionNames) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setOptionNames(optionNames);
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setOptionEnabledState(boolean enabled, int atIndex) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setOptionEnabledState(enabled, atIndex);
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setColors(Color... colors) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setColors(colors);
        }

        @Override
        public FieldEntryTextDialogImageSourceBuilder setEscapeExecution(Runnable r) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setEscapeExecution(r);
        }

        public FieldEntryTextDialogImageSourceBuilder setBackgroundColor(Color color) {
            SRC.colors[BG_COLOR] = color;
            return this;
        }

        public FieldEntryTextDialogImageSourceBuilder setTitleColor(Color color) {
            SRC.colors[TITLE_COLOR] = color;
            return this;
        }

        public FieldEntryTextDialogImageSourceBuilder setEnabledColors(Color fg, Color bg) {
            SRC.colors[ENABLED_OPTION_PRIMARY_COLOR] = fg;
            SRC.colors[ENABLED_OPTION_SECONDARY_COLOR] = bg;
            return this;
        }

        public FieldEntryTextDialogImageSourceBuilder setFieldColors(Color fg, Color bg) {
            SRC.colors[DISABLED_OPTION_PRIMARY_COLOR] = fg;
            SRC.colors[DISABLED_OPTION_SECONDARY_COLOR] = bg;
            return this;
        }

        public FieldEntryTextDialogImageSourceBuilder setSelectedColors(Color fg, Color bg) {
            SRC.colors[SELECTED_OPTION_PRIMARY_COLOR] = fg;
            SRC.colors[SELECTED_OPTION_SECONDARY_COLOR] = bg;
            return this;
        }

        public FieldEntryTextDialogImageSourceBuilder setInputFieldCharacterLimits(int... i) {
            ((FieldEntryTextDialogImageSource)SRC).inputSizeLimits = i;
            return this;
        }

        /**
         * Override setOptionEnabledState with a more appropriate name.
         */
        public FieldEntryTextDialogImageSourceBuilder setInputFieldMaskState(boolean maskState, int atIndex) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setOptionEnabledState(!maskState, atIndex);
        }

        public FieldEntryTextDialogImageSourceBuilder prePopulateField(String fieldText, int atIndex) {
            int[] lim = ((FieldEntryTextDialogImageSource)SRC).inputSizeLimits;
            if (lim == null) {
                throw new IllegalStateException("Field limits have not been set.");
            }
            if (fieldText.length() >= lim[atIndex]) {
                throw new IllegalArgumentException("Argument [" + fieldText + "] exceeds field limit of " +
                        lim[atIndex] + ".");
            }
            initializeFields();
            ((FieldEntryTextDialogImageSource)SRC).inputFields[atIndex] = fieldText;
            return this;
        }

        public FieldEntryTextDialogImageSourceBuilder setSubmitExecution(Runnable runnable) {
            ((FieldEntryTextDialogImageSource)SRC).submitExecution = runnable;
            return this;
        }

    }

    String[] inputFields;
    int[] inputSizeLimits;
    Runnable submitExecution;
    //todo - error message field - we can set this if submit fails

    private FieldEntryTextDialogImageSource(
    ){
        selectedOption = 0;
        colors[DISABLED_OPTION_PRIMARY_COLOR] = Color.BLACK; //input field text
        colors[DISABLED_OPTION_SECONDARY_COLOR] = Color.WHITE; //input field bg
        submitExecution = () -> {throw new IllegalStateException("Unspecified submission execution!");};
    }

    private String maskInputField(int index) {
        return optionEnabledStates[index] ? inputFields[index] : "*".repeat(inputFields[index].length());
    }

    @Override
    public BufferedImage image() {
        Dimension d = size();
        BufferedImage result = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        int width = TextImager.measureText(title, textHeight).width;
        ImageComposer.superimpose(
                TextImager.image(
                            title,
                            new TextImageSourceConfiguration(
                            textHeight,
                            width,
                            colors[TITLE_COLOR],
                            colors[BG_COLOR]
                        )
                ),
                result,
                new Point((imageDimension.width - width) / 2, 0)
        );
        for (int i = 0; i < optionCount; ++i) {
            width = TextImager.measureText(optionNames[i], textHeight).width;
            ImageComposer.superimpose(
                    TextImager.image(
                            optionNames[i],
                            new TextImageSourceConfiguration(
                                textHeight,
                                width,
                                colors[
                                        selectedOption == i
                                                ? SELECTED_OPTION_PRIMARY_COLOR
                                                : ENABLED_OPTION_PRIMARY_COLOR
                                        ],
                                colors[
                                        selectedOption == i
                                                ? SELECTED_OPTION_SECONDARY_COLOR
                                                : ENABLED_OPTION_SECONDARY_COLOR
                                        ]
                            )
                    ),
                    result,
                    new Point((imageDimension.width / 2) - width, (i + 2) * textHeight + i)
            );
            Point offset = new Point(imageDimension.width / 2, (i + 2) * textHeight + i);
            ImageComposer.superimpose(
                    TextImager.imageEmptyField(
                            inputSizeLimits[i],
                            textHeight,
                            colors[DISABLED_OPTION_SECONDARY_COLOR]
                    ),
                    result,
                    offset
            );
            String maskedFieldText = maskInputField(i);
            if (!maskedFieldText.isEmpty()) {
                ImageComposer.superimpose(
                        TextImager.image(
                                maskInputField(i),
                                new TextImageSourceConfiguration(
                                    textHeight,
                                    TextImager.measureText(maskedFieldText, textHeight).width,
                                    colors[DISABLED_OPTION_PRIMARY_COLOR],
                                    colors[DISABLED_OPTION_SECONDARY_COLOR]
                                )
                        ),
                        result,
                        offset
                );
            }
        }
        return result;
    }

    @Override
    public void inform(@NotNull Point imageCoordinates) {
        //todo?
    }

    @Override
    public void input(KeyEvent e) {
        Character inputChar = InputEventUtils.toChar(e);
        if (inputChar != null) {
            if (inputFields[selectedOption].length() < inputSizeLimits[selectedOption]) {
                inputFields[selectedOption] += inputChar;
            }
        } else {
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_BACK_SPACE: case KeyEvent.VK_DELETE:
                    if (!inputFields[selectedOption].isEmpty()) {
                        inputFields[selectedOption] =
                                inputFields[selectedOption].substring(0, inputFields[selectedOption].length() - 1);
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (selectedOption == optionCount - 1) {
                        submitExecution.run();
                    } else {
                        ++selectedOption;
                    }
                    break;
                case KeyEvent.VK_TAB: //todo - this is currently not registering - why?
                    if (InputEventUtils.hasModifier(KeyEvent.SHIFT_DOWN_MASK, e)) {
                        if (selectedOption > 0) {
                            --selectedOption;
                        }
                    } else if (selectedOption < optionCount - 1) {
                        ++selectedOption;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    escape();
                    break;
                case KeyEvent.VK_UP:
                    if (--selectedOption < 0) {
                        selectedOption = optionCount -1;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (++selectedOption >= optionCount) {
                        selectedOption = 0;
                    }
                    break;
                default: //do nothing
                    break;
            }
        }
    }

    @Override
    public void invoke(Point imageCoordinates) {
        if (imageCoordinates == null) return;
        int optionIndex = getOptionIndex(imageCoordinates.y);
        if (optionIndex >= 0) {
            selectedOption = optionIndex;
        }
    }

    public String[] readFields() {
        return inputFields;
    }

    public static FieldEntryTextDialogImageSourceBuilder builder() {
        return new FieldEntryTextDialogImageSourceBuilder();
    }
}
