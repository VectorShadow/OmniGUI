package vsdl.omnigui.api.message;

public interface MessageSource {
    void addMessenger(Messenger messenger);
    void notify(Message message);
}
