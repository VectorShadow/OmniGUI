package vsdl.omnigui.api.message;

public class Message {
    private final String messageText;
    private final String messageType;

    public String getMessageText() {
        return messageText;
    }

    public String getMessageType() {
        return messageType;
    }

    public Message(String messageText, String messageType) {
        this.messageText = messageText;
        this.messageType = messageType;
    }
}
