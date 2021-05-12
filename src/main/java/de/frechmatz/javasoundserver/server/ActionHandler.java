package de.frechmatz.javasoundserver.server;

import de.frechmatz.javasoundserver.message.FramesMessage;
import de.frechmatz.javasoundserver.message.InitMessage;

public interface ActionHandler {
    void init(InitMessage message) throws Exception;
    void ackInit() throws Exception;
    void requestFrames() throws Exception;
    void frames(FramesMessage message) throws Exception;
    void start() throws Exception;
    void stop() throws Exception;
    void close() throws Exception;
}
