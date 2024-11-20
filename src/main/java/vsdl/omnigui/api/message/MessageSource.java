package vsdl.omnigui.api.message;

import org.vsdl.common.mmo.comm.Message;

public interface MessageSource {
    void addMessenger(Messenger messenger);
    void notify(Message message);
}
