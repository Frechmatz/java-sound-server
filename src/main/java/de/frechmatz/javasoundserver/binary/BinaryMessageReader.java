package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageReader;

import java.io.DataInputStream;
import java.io.IOException;

public class BinaryMessageReader implements MessageReader {
    private final DataInputStream is;

    public BinaryMessageReader(DataInputStream is) {
        this.is = is;
    }

    @Override
    public Message read() throws IOException {
        return BinaryMessageFormat.read(is);
    }
}
