package de.frechmatz.javasoundserver.api;

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
