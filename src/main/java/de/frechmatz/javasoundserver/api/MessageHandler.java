package de.frechmatz.javasoundserver.api;

public interface MessageHandler {
    void onMessage(CloseMessage message) throws Exception;
    void onMessage(FramesMessage message) throws Exception;
    void onMessage(InitMessage message) throws Exception;
    void onMessage(AckInitMessage message) throws Exception;
    void onMessage(GetFramesMessage message) throws Exception;
    void onMessage(StartMessage message) throws Exception;
    void onMessage(StopMessage message) throws Exception;
}
