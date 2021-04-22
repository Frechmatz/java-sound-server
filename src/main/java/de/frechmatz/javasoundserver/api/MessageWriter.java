package de.frechmatz.javasoundserver.api;

import de.frechmatz.javasoundserver.message.*;

import java.io.IOException;

public interface MessageWriter {
    void write(AckMessage message) throws Exception;
    void write(NakMessage message) throws Exception;
    void write(CloseMessage message) throws Exception;
    void write(FramesMessage message) throws Exception;
    void write(InitMessage message) throws Exception;
    void write(GetFramesMessage message) throws Exception;
    void write(StartMessage message) throws Exception;
    void write(StopMessage message) throws Exception;
}
