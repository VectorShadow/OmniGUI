package vsdl.omnigui.image.source;

import org.vsdl.common.mmo.comm.Message;
import vsdl.omnigui.api.message.MessageDestination;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import static java.util.Objects.nonNull;
import static vsdl.omnigui.image.ImageComposer.superimpose;

public class HistoricalTextDisplayAreaImageSource implements InteractiveImageSource, MessageDestination {

    private final int messageLimit;
    private final TextImageSourceConfiguration config;
    private final Color freshForegroundColor;
    private final boolean displayFreshMessageAtTop;

    private final Deque<String> messageDeque = new LinkedList<>();
    private InteractiveTextImageSource freshImageSource = null;
    private final ArrayDeque<InteractiveTextImageSource> staleImageSources = new ArrayDeque<>();

    public HistoricalTextDisplayAreaImageSource(
            final int messageLimit,
            final TextImageSourceConfiguration config,
            final Color freshForegroundColor,
            final boolean displayFreshMessageAtTop
    ) {
        this.messageLimit = messageLimit;
        this.config = config;
        this.freshForegroundColor = freshForegroundColor;
        this.displayFreshMessageAtTop = displayFreshMessageAtTop;
        this.freshImageSource = getBlankImageSource();
    }

    public void addMessage(final String message) {
        messageDeque.addFirst(message);
        if (messageDeque.size() > messageLimit) {
            messageDeque.removeLast();
        }
        if (nonNull(freshImageSource)) {
            staleImageSources.addFirst(
                    new InteractiveTextImageSource(
                            freshImageSource.TEXT,
                            config.clone(),
                            () -> {}
                    )
            );
            if (staleImageSources.size() >= messageLimit) {
                staleImageSources.removeLast();
            }
        }
        freshImageSource = new InteractiveTextImageSource(
                message,
                new TextImageSourceConfiguration(
                    config.getLineHeight(),
                    config.getTextAreaDimension(),
                    config.getTextBackgroundColor(),
                    freshForegroundColor
                ),
                () -> {}
        );
    }

    private InteractiveTextImageSource getBlankImageSource() {
        return new InteractiveTextImageSource(
                "",
                config.clone(),
                () -> {}
        );
    }

    @Override
    public BufferedImage image() {
        BufferedImage image = new BufferedImage(size().width, size().height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < messageLimit; ++i) {
            int yIndex = displayFreshMessageAtTop
                    ? i * config.getTextAreaDimension().height
                    : size().height - (i + 1) * config.getTextAreaDimension().height;
            BufferedImage image0 =
                    i == 0
                            ? freshImageSource.image()
                            : staleImageSources.size() >= i
                                ? ((InteractiveTextImageSource)staleImageSources.toArray()[i - 1]).image()
                                : getBlankImageSource().image();
            superimpose(image0, image, new Point(0, yIndex));
        }
        return image;
    }

    @Override
    public void inform(Point imageCoordinates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void input(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void invoke(Point imageCoordinates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Dimension size() {
        return new Dimension(
                config.getTextAreaDimension().width,
                config.getTextAreaDimension().height * messageLimit
        );
    }

    @Override
    public void receiveMessage(Message message) {
        addMessage(message.getMessageContent().toString());
    }
}
