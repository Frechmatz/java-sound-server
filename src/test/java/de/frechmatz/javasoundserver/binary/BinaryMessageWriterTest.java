package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.CloseMessage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryMessageWriterTest {

    @Test
    void testCloseMessage() throws Exception {
        final CloseMessage message = new CloseMessage();
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final DataOutputStream os = new DataOutputStream(bos);
        BinaryMessageWriter writer = new BinaryMessageWriter(os);
        writer.write(message);
        os.flush();
        byte[] arr = bos.toByteArray();
        // Start-Of-Message Marker
        assertEquals(1, arr[0]);
        assertEquals(2, arr[1]);
        assertEquals(3, arr[2]);
        assertEquals(4, arr[3]);
        // 16 Bit Message Type
        assertEquals(0, arr[4]);
        assertEquals(Constants.MESSAGE_TYPE_CLOSE, arr[5]);
        // End-Of-Message Marker
        assertEquals(4, arr[6]);
        assertEquals(3, arr[7]);
        assertEquals(2, arr[8]);
        assertEquals(1, arr[9]);
    }

}
