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
            final int H = SRC.textHeight * (SRC.optionCount + 2); //title, empty line, one line per field
            int maxWidth = TextImager.measureText(SRC.title, SRC.textHeight).width;
            System.out.println("Max Width after title: " + maxWidth);
            for (int i = 0; i < SRC.optionCount; ++i) {
                Dimension dName = TextImager.measureText(
                        SRC.optionNames[i],
                        SRC.textHeight
                );
                Dimension dField = TextImager.measureEmptyField(
                        ((FieldEntryTextDialogImageSource)SRC).inputSizeLimits[i],
                        SRC.textHeight
                );
                System.out.println("Name width: " + dName.width + "; field width: " + dField.width);
                int width = Math.max(2 * dName.width, 2 * dField.width);
                if (width > maxWidth) {
                    maxWidth = width;
                }
                System.out.println("Max Width after option: " + maxWidth);
            }
            return new Dimension(maxWidth, H);
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
            ((FieldEntryTextDialogImageSource) SRC).inputFields = new String[SRC.optionCount];
            for (int i = 0; i < SRC.optionCount; ++i) {
                ((FieldEntryTextDialogImageSource) SRC).inputFields[i] = "";
            }
            //todo?
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

        //todo - override color by index

        @Override
        public FieldEntryTextDialogImageSourceBuilder setEscapeExecution(Runnable r) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setEscapeExecution(r);
        }

        public FieldEntryTextDialogImageSourceBuilder setInputFieldCharacterLimits(int... i) {
            ((FieldEntryTextDialogImageSource)SRC).inputSizeLimits = i;
            return this;
        }

        /**
         * Override setOptionEnabledState with a more appropriate name.
         */
        public FieldEntryTextDialogImageSourceBuilder setInputFieldMaskState(boolean maskState, int atIndex) {
            return (FieldEntryTextDialogImageSourceBuilder) super.setOptionEnabledState(maskState, atIndex);
        }

        public FieldEntryTextDialogImageSourceBuilder setSubmitExecution(Runnable runnable) {
            ((FieldEntryTextDialogImageSource)SRC).submitExecution = runnable;
            return this;
        }

    }

    String[] inputFields;
    int[] inputSizeLimits;
    Runnable submitExecution;

    private int selectedField;

    private FieldEntryTextDialogImageSource(
    ){
        selectedField = 0;
        colors[DISABLED_OPTION_PRIMARY_COLOR] = Color.BLACK; //input field text
        colors[DISABLED_OPTION_SECONDARY_COLOR] = Color.WHITE; //input field bg
        submitExecution = () -> {throw new IllegalStateException("Unspecified submission execution!");};
    }

    private String maskInputField(int index) {
        if (optionEnabledStates[index]) {
            return inputFields[index];
        }
        return "*".repeat(inputFields[index].length());
    }

    @Override
    public BufferedImage image() {
        Dimension d = size();
        BufferedImage result = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        int width = TextImager.measureText(title, textHeight).width;
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
                new Point((imageDimension.width - width) / 2, 0)
        );
        for (int i = 0; i < optionCount; ++i) {
            width = TextImager.measureText(optionNames[i], textHeight).width;
            ImageComposer.superimpose(
                    TextImager.image(
                            optionNames[i],
                            textHeight,
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
                    ),
                    result,
                    new Point((imageDimension.width / 2) - width, (i + 2) * textHeight)
            );
            width = TextImager.measureEmptyField(inputSizeLimits[i], textHeight).width;
            ImageComposer.superimpose(
                    TextImager.image(
                            maskInputField(i),
                            textHeight,
                            textHeight,
                            width,
                            colors[DISABLED_OPTION_PRIMARY_COLOR],
                            colors[DISABLED_OPTION_SECONDARY_COLOR]
                    ),
                    result,
                    new Point(imageDimension.width / 2, (i + 2) * textHeight)
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
        Character inputChar = InputEventUtils.toChar(e);
        if (inputChar != null) {
            if (inputFields[selectedField].length() < inputSizeLimits[selectedField]) {
                inputFields[selectedField] += inputChar;
            }
        } else {
            switch (e.getExtendedKeyCode()) {
                case KeyEvent.VK_BACK_SPACE: case KeyEvent.VK_DELETE:
                    if (inputFields[selectedField].length() > 0) {
                        inputFields[selectedField] =
                                inputFields[selectedField].substring(0, inputFields[selectedField].length() - 1);
                    }
                    break;
                case KeyEvent.VK_TAB: case KeyEvent.VK_ENTER:
                    if (selectedField == optionCount - 1) {
                        submitExecution.run();
                    } else {
                        ++selectedField;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    escape();
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

    public static FieldEntryTextDialogImageSourceBuilder builder() {
        return new FieldEntryTextDialogImageSourceBuilder();
    }
}
