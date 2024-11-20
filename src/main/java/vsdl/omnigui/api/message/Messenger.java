package vsdl.omnigui.api.message;

import java.util.ArrayList;
import java.util.List;

public class Messenger {
    List<MessageDestination> messageDestinations;

    public Messenger() {
        messageDestinations = new ArrayList<>();
    }

    public void addDestination(MessageDestination destination) {
        messageDestinations.add(destination);
    }

    public void send(Message message) {
        messageDestinations.forEach(destination -> destination.receiveMessage(message));
    }
}
