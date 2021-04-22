package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.message.*;

import java.io.DataOutputStream;

public class BinaryMessageWriter implements MessageWriter {
    private final DataOutputStream os;

    public BinaryMessageWriter(DataOutputStream os) {
        this.os = os;
    }

    @Override
    public void write(AckMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }

    @Override
    public void write(NakMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }

    @Override
    public void write(CloseMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }

    @Override
    public void write(FramesMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }

    @Override
    public void write(InitMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }

    @Override
    public void write(GetFramesMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }

    @Override
    public void write(StartMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }

    @Override
    public void write(StopMessage message) throws Exception {
        MessageMarker.writeStart(os);
        BinaryMessageFormat.write(os, message);
        MessageMarker.writeEnd(os);
    }
}
