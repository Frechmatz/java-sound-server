package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.message.*;

import java.io.DataOutputStream;
import java.io.IOException;

public class BinaryMessageWriter implements MessageWriter {
    private final DataOutputStream os;

    public BinaryMessageWriter(DataOutputStream os) {
        this.os = os;
    }

    @Override
    public void write(AckMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }

    @Override
    public void write(NakMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }

    @Override
    public void write(CloseMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }

    @Override
    public void write(FramesMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }

    @Override
    public void write(InitMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }

    @Override
    public void write(GetFramesMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }

    @Override
    public void write(StartMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }

    @Override
    public void write(StopMessage message) throws IOException {
        BinaryMessageFormat.write(os, message);
    }
}
