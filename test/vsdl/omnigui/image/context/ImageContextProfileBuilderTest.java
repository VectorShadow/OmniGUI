package vsdl.omnigui.image.context;

import org.junit.jupiter.api.Test;
import vsdl.omnigui.fixtures.ImageContextFixture;

import static org.junit.jupiter.api.Assertions.*;
import static vsdl.omnigui.fixtures.ImageContextFixture.getImageContext;

public class ImageContextProfileBuilderTest {

    @Test
    void testBuildWithoutData() {
        assertThrows(IllegalStateException.class, () -> ImageContextProfileBuilder.start().build());
    }

    @Test
    void testAppendContextWithoutHotkeys() {
        ImageContext expectedContext = ImageContextFixture.getImageContext();
        ImageContextProfile icp = ImageContextProfileBuilder.start().appendImageContext(expectedContext).build();
        assertEquals(expectedContext, icp.getHierarchy().getUppermost());
        assertTrue(icp.getHotkeys().isEmpty());
    }

    @Test
    void testAppendContextWithHotkeys() {
        ImageContextProfile icp = ImageContextProfileBuilder.start().appendImageContext(getImageContext(), 1).build();
        assertFalse(icp.getHotkeys().isEmpty());
    }

    @Test
    void testOverrideTransparency() {
        int expectedRGBIgnore = 1;
        ImageContextProfile icp = ImageContextProfileBuilder.start()
                .appendImageContext(getImageContext(), 1)
                .overrideTransparency(expectedRGBIgnore)
                .build();
        assertEquals(expectedRGBIgnore, icp.getRgbIgnore());
    }

    @Test
    void testOverrideTransparencyWithoutContext() {
        assertThrows(IllegalStateException.class, () -> ImageContextProfileBuilder.start()
                .overrideTransparency(1)
                .build());
    }
}
