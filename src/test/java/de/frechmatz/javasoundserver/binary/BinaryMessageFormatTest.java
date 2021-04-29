package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.message.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryMessageFormatTest {

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
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_ACK, // 16 bit message type
                END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof AckMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testNakMessage() throws Exception {
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_NAK, // 16 bit message type
                END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof NakMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testCloseMessage() throws Exception {
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_CLOSE, // 16 bit message type
                END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof CloseMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testStartMessage() throws Exception {
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_START, // 16 bit message type
                END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof StartMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testStopMessage() throws Exception {
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_STOP, // 16 bit message type
                END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof StopMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testGetFramesMessage() throws Exception {
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_GET_FRAMES, // 16 bit message type
                END_MARKER_1, END_MARKER_2, END_MARKER_3};
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof GetFramesMessage);
        assertEndOfMessageMarker(is);
    }

    @Test
    void testInitMessage() throws Exception {
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_INIT, // 16 bit message type
                0,0,0,20, // Sample-Rate
                0, 2, // Sample-Width
                0,4, // Channel-Count
                0,0,0,50, // Buffer-Size
                0,1, // Omit-Audio-Output
                END_MARKER_1, END_MARKER_2, END_MARKER_3
        };
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof InitMessage);
        InitMessage initMessage = (InitMessage)msg;
        assertEquals(20, initMessage.getSampleRate());
        assertEquals(2, initMessage.getSampleWidth());
        assertEquals(4, initMessage.getChannelCount());
        assertEquals(50, initMessage.getBufferSizeFrames());
        assertEquals(1, initMessage.getOmitAudioOutput());
        assertEndOfMessageMarker(is);
    }

    @Test
    void testFramesMessage() throws Exception {
        final byte[] data = {
                0, Constants.MESSAGE_TYPE_FRAMES, // 16 bit message type
                0,0,0,4, // Sample-Data-Count
                0,1, // Sample 1
                0,2, // Sample 2
                END_MARKER_1, END_MARKER_2, END_MARKER_3
        };
        final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
        Message msg = BinaryMessageFormat.read(is);
        assertTrue(msg instanceof FramesMessage);
        FramesMessage framesMessage = (FramesMessage)msg;
        assertEquals(4, framesMessage.getSampleData().length);
        assertEquals(0, framesMessage.getSampleData()[0]);
        assertEquals(1, framesMessage.getSampleData()[1]);
        assertEquals(0, framesMessage.getSampleData()[2]);
        assertEquals(2, framesMessage.getSampleData()[3]);
        assertEndOfMessageMarker(is);
    }

}
