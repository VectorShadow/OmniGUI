package vsdl.omnigui.image.source;

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
            for (int i = 0; i < SRC.optionCount; ++i) {
                Dimension dName = TextImager.measureText(
                        ((FieldEntryTextDialogImageSource)SRC).inputFields[i],
                        SRC.textHeight
                );
                Dimension dField = TextImager.measureEmptyField(
                        ((FieldEntryTextDialogImageSource)SRC).inputSizeLimits[i],
                        SRC.textHeight
                );
                int width = dName.width + dField.width;
                if (width > maxWidth) {
                    maxWidth = width;
                }
            }
            return new Dimension(maxWidth, H);
        }

        @Override
        public InteractiveTextDialogImageSource build() {
            if (SRC.optionNames == null || SRC.optionCount == 0) {
                throw new IllegalArgumentException("Number of input fields must be > 0.");
            }
            if (((FieldEntryTextDialogImageSource)SRC).inputSizeLimits.length != SRC.optionCount ||
                    ((FieldEntryTextDialogImageSource)SRC).optionEnabledStates.length != SRC.optionCount
            ) {
                throw new IllegalArgumentException(
                        "Number of field names, size limits, and masks must be the same."
                );
            }
            //todo?
            return super.build();
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

    }

    String[] inputFields;
    int[] inputSizeLimits;

    private int selectedField;

    private FieldEntryTextDialogImageSource(
    ){
        selectedField = 0;
        colors[DISABLED_OPTION_PRIMARY_COLOR] = Color.BLACK; //input field text
        colors[DISABLED_OPTION_SECONDARY_COLOR] = Color.WHITE; //input field bg
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

    public static InteractiveTextDialogImageSourceBuilder builder() {
        return new FieldEntryTextDialogImageSourceBuilder();
    }
}
