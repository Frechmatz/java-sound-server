package de.frechmatz.javasoundserver.api;

import de.frechmatz.javasoundserver.message.FramesMessage;
import de.frechmatz.javasoundserver.message.InitMessage;

public interface ActionHandler {
    void ack() throws Exception;
    void nak() throws Exception;
    void init(InitMessage message) throws Exception;
    void requestFrames() throws Exception;
    void frames(FramesMessage message) throws Exception;
    void start() throws Exception;
    void stop() throws Exception;
    void close() throws Exception;
}
