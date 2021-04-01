package de.frechmatz.javasoundserver.api;

public interface Message {
    /**
     *
     * @param handler
     */
    void dispatch(MessageHandler handler) throws Exception;
}
