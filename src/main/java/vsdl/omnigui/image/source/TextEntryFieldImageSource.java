package vsdl.omnigui.image.source;

import vsdl.omnigui.api.message.Message;
import vsdl.omnigui.api.message.MessageSource;
import vsdl.omnigui.api.message.Messenger;
import vsdl.omnigui.api.util.KeyboardUtils;
import vsdl.omnigui.image.TextImager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class TextEntryFieldImageSource implements InteractiveImageSource, MessageSource {

    private String inputText = "";

    private final TextImageSourceConfiguration config;
    private final String prompt;

    private final ArrayList<Messenger> messengerList = new ArrayList<>();

    public TextEntryFieldImageSource(TextImageSourceConfiguration config, String prompt) {
        this.config = config;
        this.prompt = prompt;
    }

    @Override
    public BufferedImage image() {
        return TextImager.image(
                prompt + inputText,
                config
        );
    }

    @Override
    public void inform(Point imageCoordinates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void input(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (!inputText.isEmpty()) {
                    notify(new Message(inputText, ""));
                    inputText = "";
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                if (!inputText.isEmpty()) {
                    inputText = inputText.substring(0, inputText.length() - 1);
                }
                break;
            default:
                Character c = KeyboardUtils.toChar(e.getKeyCode(), e.getModifiersEx());
                if (nonNull(c)) {
                    inputText = inputText + c;
                }
        }
    }

    @Override
    public void invoke(Point imageCoordinates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Dimension size() {
        return config.getTextAreaDimension();
    }

    @Override
    public void addMessenger(Messenger messenger) {
        messengerList.add(messenger);
    }

    @Override
    public void notify(Message message) {
        messengerList.forEach(messenger -> messenger.send(message));
    }
}
