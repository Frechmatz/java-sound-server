package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageReader;

import java.io.DataInputStream;

public class BinaryMessageReader implements MessageReader {
    private final DataInputStream is;
    private final BinaryMessageFormat reader;

    public BinaryMessageReader(DataInputStream is) {
        this.is = is;
        this.reader = new BinaryMessageFormat(is, null);
    }

    @Override
    public Message read() throws Exception {
        MessageMarker.readStart(is);
        Message message = reader.read();
        MessageMarker.readEnd(is);
        return message;
    }
}
