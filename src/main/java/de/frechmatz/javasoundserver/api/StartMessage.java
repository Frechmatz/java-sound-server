package de.frechmatz.javasoundserver.api;

public class StartMessage implements Message {

    public StartMessage() {
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "StartMessage";
    }
}
