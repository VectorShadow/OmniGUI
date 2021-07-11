package vsdl.omnigui.image.context;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ImageContextHierarchy implements Iterable<ImageContext> {
    private final ArrayList<ImageContext> IMAGE_CONTEXT_LIST;

    ImageContextHierarchy() {
        IMAGE_CONTEXT_LIST = new ArrayList<>();
    }

    void append(ImageContext imageContext) {
        IMAGE_CONTEXT_LIST.add(imageContext);
    }

    ImageContext find(Point p) {
        for (int i = IMAGE_CONTEXT_LIST.size() - 1; i > -1; --i) {
            if (IMAGE_CONTEXT_LIST.get(i).contains(p)) return IMAGE_CONTEXT_LIST.get(i);
        }
        return null;
    }

    @NotNull
    @Override
    public Iterator<ImageContext> iterator() {
        return IMAGE_CONTEXT_LIST.iterator();
    }
}
