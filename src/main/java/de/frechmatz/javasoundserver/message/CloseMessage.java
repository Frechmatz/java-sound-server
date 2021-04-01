package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.api.Message;

public class CloseMessage implements Message {

    public CloseMessage() {
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "CloseMessage";
    }
}
