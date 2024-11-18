package vsdl.omnigui.image.source;

import java.awt.*;

public abstract class InteractiveTextDialogImageSourceBuilder {

    protected InteractiveTextDialogImageSource SRC;

    abstract Dimension calculateImageDimension();

    public InteractiveTextDialogImageSource build() {
        if (SRC.optionNames.length != SRC.optionCount) {
            throw new IllegalStateException("Number of option names(" + SRC.optionNames.length +
                    ") does not match option count(" + SRC.optionCount + ").");
        }
        if (SRC.optionEnabledStates.length != SRC.optionCount) {
            throw new IllegalStateException("Number of option enabled states(" + SRC.optionEnabledStates.length +
                    ") does not match option count(" + SRC.optionCount + ").");
        }
        SRC.imageDimension = calculateImageDimension();
        return SRC;
    }

    public InteractiveTextDialogImageSourceBuilder setTitle(String title) {
        SRC.title = title;
        return this;
    }

    public InteractiveTextDialogImageSourceBuilder setTextHeight(int textHeight) {
        SRC.textHeight = textHeight;
        return this;
    }

    protected void setOptionEnabledStates() {
        if (SRC.optionEnabledStates != null) return;
        if (SRC.optionCount == 0) {
            throw new IllegalStateException("Option count is 0.");
        }
        SRC.optionEnabledStates = new boolean[SRC.optionCount];
        for (int i = 0; i < SRC.optionCount; ++i) {
            SRC.optionEnabledStates[i] = true;
        }
    }

    public InteractiveTextDialogImageSourceBuilder setOptionCount(int optionCount) {
        return setOptionCount(optionCount, 0);
    }

    public InteractiveTextDialogImageSourceBuilder setOptionCount(int optionCount, int defaultSelectedOptionIndex) {
        if (optionCount < 0) {
            throw new IllegalArgumentException("Option count may not be negative.");
        }
        if (defaultSelectedOptionIndex < 0) {
            throw new IllegalArgumentException("Default option may not have negative index.");
        }
        if (defaultSelectedOptionIndex >= optionCount) {
            throw new IllegalArgumentException("Default option index must be less than option count.");
        }
        SRC.optionCount = optionCount;
        SRC.selectedOption = defaultSelectedOptionIndex;
        setOptionEnabledStates();
        return this;
    }

    public InteractiveTextDialogImageSourceBuilder setOptionNames(String... optionNames) {
        SRC.optionNames = optionNames;
        return this;
    }

    public InteractiveTextDialogImageSourceBuilder setOptionEnabledState(boolean enabled, int atIndex) {
        if (SRC.optionEnabledStates == null) {
            setOptionEnabledStates();
        }
        SRC.optionEnabledStates[atIndex] = enabled;
        return this;
    }

    public InteractiveTextDialogImageSourceBuilder setColors(Color... colors) {
        if (colors.length > InteractiveTextDialogImageSource.COLOR_COUNT) {
            throw new IllegalArgumentException("Number of arguments exceeds limit of " +
                    InteractiveTextDialogImageSource.COLOR_COUNT + ".");
        }
        for (int i = 0; i < colors.length; ++i) {
            SRC.colors[i] = colors[i];
        }
        return this;
    }

    protected InteractiveTextDialogImageSourceBuilder setColor(Color color, int atIndex) {
        SRC.colors[atIndex] = color;
        return this;
    }

    public InteractiveTextDialogImageSourceBuilder setEscapeExecution(Runnable r) {
        SRC.escapeExecution = r;
        return this;
    }
}
