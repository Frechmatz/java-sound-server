package de.frechmatz.javasoundserver.server;

import de.frechmatz.javasoundserver.api.Message;

public class ProtocolException extends Exception {
    private final Message message;

    public ProtocolException(final Message message) {
        super();
        this.message = message;
    }

    @Override
    public String toString() {
        return "ProtocolException{" +
                "message=" + message +
                '}';
    }

}
