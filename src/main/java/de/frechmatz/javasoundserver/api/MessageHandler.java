package de.frechmatz.javasoundserver.api;

import de.frechmatz.javasoundserver.message.*;

public interface MessageHandler {
    void onMessage(AckMessage message) throws Exception;
    void onMessage(CloseMessage message) throws Exception;
    void onMessage(FramesMessage message) throws Exception;
    void onMessage(InitMessage message) throws Exception;
    void onMessage(NakMessage message) throws Exception;
    void onMessage(GetFramesMessage message) throws Exception;
    void onMessage(StartMessage message) throws Exception;
    void onMessage(StopMessage message) throws Exception;
}
