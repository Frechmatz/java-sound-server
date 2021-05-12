package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.message.CloseMessage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryMessageReaderTest {

    @Test
    void testCloseMessage() throws Exception {
        final byte[] data = {
                1,2,3,4, // Start-Of-Message Marker
                0, Constants.MESSAGE_TYPE_CLOSE, // 16 bit message type
                4,3,2,1 // End-Of-Message Marker
        };
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        BinaryMessageReader reader = new BinaryMessageReader(is);
        Message msg = reader.read();
        assertTrue(msg instanceof CloseMessage);
    }
}
