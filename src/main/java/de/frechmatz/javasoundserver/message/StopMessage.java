package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.api.Message;

public class StopMessage implements Message {

    public StopMessage() {
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "StopMessage";
    }
}
