package de.frechmatz.javasoundserver.api;

import de.frechmatz.javasoundserver.message.*;

import java.io.IOException;

public interface MessageWriter {
    void write(AckMessage message) throws IOException;
    void write(NakMessage message) throws IOException;
    void write(CloseMessage message) throws IOException;
    void write(FramesMessage message) throws IOException;
    void write(InitMessage message) throws IOException;
    void write(GetFramesMessage message) throws IOException;
    void write(StartMessage message) throws IOException;
    void write(StopMessage message) throws IOException;
}
