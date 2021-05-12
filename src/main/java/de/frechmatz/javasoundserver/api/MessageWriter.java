package de.frechmatz.javasoundserver.api;

public interface MessageWriter {
    void write(CloseMessage message) throws Exception;
    void write(FramesMessage message) throws Exception;
    void write(InitMessage message) throws Exception;
    void write(GetFramesMessage message) throws Exception;
    void write(StartMessage message) throws Exception;
    void write(StopMessage message) throws Exception;
    void write(AckInitMessage message) throws Exception;
}
