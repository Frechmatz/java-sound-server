package de.frechmatz.javasoundserver.api;

public class GetFramesMessage implements Message {

    public GetFramesMessage() {
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "GetFramesMessage{}";
    }
}
