package vsdl.omnigui.image.context;

public class ImageContextProfileBuilder {
    private final ImageContextProfile PROFILE;

    private ImageContextProfileBuilder() {
        PROFILE = new ImageContextProfile();
    }

    public static ImageContextProfileBuilder start() {
        return new ImageContextProfileBuilder();
    }

    public ImageContextProfileBuilder appendImageContext(
            ImageContext imageContext,
            int... extendedKeyCodeAndModifiers
    ) {
        for (int extendedKeyCodeAndModifier : extendedKeyCodeAndModifiers) {
            PROFILE.HOTKEYS.put(extendedKeyCodeAndModifier, imageContext);
        }
        return this.appendImageContext(imageContext);
    }

    public ImageContextProfileBuilder appendImageContext(ImageContext imageContext) {
        PROFILE.ICH.append(imageContext);
        return this;
    }

    public ImageContextProfileBuilder overrideTransparency(int rgbIgnore) {
        PROFILE.rgbIgnore = rgbIgnore;
        return this;
    }

    public ImageContextProfile build() {
        return this.PROFILE;
    }
}
