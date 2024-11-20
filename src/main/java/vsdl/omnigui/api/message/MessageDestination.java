package vsdl.omnigui.api.message;

import org.vsdl.common.mmo.comm.Message;

public interface MessageDestination {
    void receiveMessage(Message message);
}
