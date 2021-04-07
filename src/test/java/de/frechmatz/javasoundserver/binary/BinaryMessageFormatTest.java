package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.message.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryMessageFormatTest {

    private static final byte MESSAGE_TYPE_ACK = 1;
    private static final byte MESSAGE_TYPE_NAK = 2;
    private static final byte MESSAGE_TYPE_FRAMES = 3;
    private static final byte MESSAGE_TYPE_GET_FRAMES = 4;
    private static final byte MESSAGE_TYPE_INIT = 5;
    private static final byte MESSAGE_TYPE_STOP = 6;
    private static final byte MESSAGE_TYPE_START = 7;
    private static final byte MESSAGE_TYPE_CLOSE = 8;

    private static final byte END_MARKER_1 = 127;
    private static final byte END_MARKER_2 = 126;
    private static final byte END_MARKER_3 = 125;

    private void assertEndOfMessageMarker(DataInputStream is) throws Exception {
        assertEquals(END_MARKER_1, is.readByte());
        assertEquals(END_MARKER_2, is.readByte());
        assertEquals(END_MARKER_3, is.readByte());
    }

    @Test
    void testInvalidMessageId() {
        final byte[] data = {127, 127};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        assertThrows(Exception.class, () -> BinaryMessageFormat.read(is));
    }

    @Test
    void testEOF() {
        final byte[] data = {};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        assertThrows(IOException.class, () -> BinaryMessageFormat.read(is));
    }

    @Test
    void testAckMessage() throws Exception {
        final byte[] data = {0, MESSAGE_TYPE_ACK, END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof AckMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testNakMessage() throws Exception {
        final byte[] data = {0, MESSAGE_TYPE_NAK, END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof NakMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testCloseMessage() throws Exception {
        final byte[] data = {0, MESSAGE_TYPE_CLOSE, END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof CloseMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testStartMessage() throws Exception {
        final byte[] data = {0, MESSAGE_TYPE_START, END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof StartMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testStopMessage() throws Exception {
        final byte[] data = {0, MESSAGE_TYPE_STOP, END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof StopMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testGetFramesMessage() throws Exception {
        final byte[] data = {0, MESSAGE_TYPE_GET_FRAMES, 0,0,0,20, END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof GetFramesMessage);
        GetFramesMessage getFramesMessage = (GetFramesMessage)msg;
        assertEquals(20, getFramesMessage.getFrameCount());
        assertEndOfMessageMarker(is);
    }

    @Test
    void testInitMessage() throws Exception {
        final byte[] data = {
                0, MESSAGE_TYPE_INIT,
                0,0,0,20, // Sample-Rate
                0,4, // Channel-Count
                0,0,0,50, // Buffer-Size
                END_MARKER_1, END_MARKER_2, END_MARKER_3
        };
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof InitMessage);
        InitMessage initMessage = (InitMessage)msg;
        assertEquals(20, initMessage.getSampleRate());
        assertEquals(4, initMessage.getChannelCount());
        assertEquals(50, initMessage.getBufferSize());
        assertEndOfMessageMarker(is);
    }

    @Test
    void testFramesMessage() throws Exception {
        final byte[] data = {
                0, MESSAGE_TYPE_FRAMES,
                0,0,0,2, // Sample-Count
                0,1, // Sample 1
                0,2, // Sample 2
                END_MARKER_1, END_MARKER_2, END_MARKER_3
        };
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof FramesMessage);
        FramesMessage framesMessage = (FramesMessage)msg;
        assertEquals(2, framesMessage.getSampleCount());
        assertEquals(1, framesMessage.getSamples()[0]);
        assertEquals(0, framesMessage.getSamples()[1]);
        assertEquals(2, framesMessage.getSamples()[2]);
        assertEquals(0, framesMessage.getSamples()[3]);
        assertEndOfMessageMarker(is);
    }

}
