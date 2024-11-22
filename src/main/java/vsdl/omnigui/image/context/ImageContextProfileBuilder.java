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
        PROFILE.appendImageContext(imageContext, extendedKeyCodeAndModifiers);
        return this;
    }

    public ImageContextProfileBuilder appendImageContext(ImageContext imageContext) {
        return appendImageContext(imageContext, new int[0]);
    }

    public ImageContextProfileBuilder overrideTransparency(int rgbIgnore) {
        PROFILE.setRgbIgnore(rgbIgnore);
        return this;
    }

    public ImageContextProfile build() {
        if (!PROFILE.getHierarchy().hasUppermost())
            throw new IllegalStateException("ImageContextProfile must contain at least one context.");
        return this.PROFILE;
    }
}
