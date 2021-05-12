package de.frechmatz.javasoundserver.api;

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
