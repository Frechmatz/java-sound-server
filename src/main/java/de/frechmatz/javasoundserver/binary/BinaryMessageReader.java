package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageReader;

import java.io.DataInputStream;

public class BinaryMessageReader implements MessageReader {
    private final DataInputStream is;

    public BinaryMessageReader(DataInputStream is) {
        this.is = is;
    }

    @Override
    public Message read() throws Exception {
        MessageMarker.readStart(is);
        Message message = BinaryMessageFormat.read(is);
        MessageMarker.readEnd(is);
        return message;
    }
}
