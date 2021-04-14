package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.message.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

final class BinaryMessageFormat {
    private BinaryMessageFormat() {
    }

    private static short readMessageType(DataInputStream is) throws IOException {
        return is.readShort();
    };

    private static void writeMessageType(DataOutputStream os, short commandId) throws IOException {
        os.writeShort(commandId);
    };

    private static int readFrameCount(DataInputStream is) throws IOException {
        return is.readInt();
    };

    private static void writeFrameCount(DataOutputStream os, int frameCount) throws IOException {
        os.writeInt(frameCount);
    };

    private static int readSampleDataLength(DataInputStream is) throws IOException {
        return is.readInt();
    };

    private static void writeSampleDataLength(DataOutputStream os, int frameCount) throws IOException {
        os.writeInt(frameCount);
    };

    private static byte[] readSampleData(DataInputStream is, int sampleDataLength) throws IOException {
        byte[] arr = new byte[sampleDataLength];
        is.readFully(arr);
        return arr;
    };

    private static void writeShortSamples(DataOutputStream os, byte[] samples) throws IOException {
        throw new RuntimeException("writeShortSamples not implemented");
    }

    private static int readSampleRate(DataInputStream is) throws IOException {
        return is.readInt();
    }

    private static void writeSampleRate(DataOutputStream os, int sampleRate) throws IOException {
        os.writeInt(sampleRate);
    }

    private static short readChannelCount(DataInputStream is) throws IOException {
        return is.readShort();
    }

    private static void writeChannelCount(DataOutputStream os, short channelCount) throws IOException {
        os.writeShort(channelCount);
    }

    private static int readBufferSize(DataInputStream is) throws IOException {
        return is.readInt();
    };

    private static void writeBufferSize(DataOutputStream os, int bufferSize) throws IOException {
        os.writeInt(bufferSize);
    };

    private static final short MESSAGE_TYPE_ACK = 1;
    private static final short MESSAGE_TYPE_NAK = 2;
    private static final short MESSAGE_TYPE_FRAMES = 3;
    private static final short MESSAGE_TYPE_GET_FRAMES = 4;
    private static final short MESSAGE_TYPE_INIT = 5;
    private static final short MESSAGE_TYPE_STOP = 6;
    private static final short MESSAGE_TYPE_START = 7;
    private static final short MESSAGE_TYPE_CLOSE = 8;

    public static void write(DataOutputStream os, AckMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_ACK);
        os.flush();
    }

    public static void write(DataOutputStream os, NakMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_NAK);
        os.flush();
    }

    public static void write(DataOutputStream os, CloseMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_CLOSE);
        os.flush();
    }

    public static void write(DataOutputStream os, FramesMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_FRAMES);
        writeSampleDataLength(os, message.getSampleData().length);
        writeShortSamples(os, message.getSampleData());
        os.flush();
    }

    public static void write(DataOutputStream os, InitMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_INIT);
        writeSampleRate(os, message.getSampleRate());
        writeChannelCount(os, message.getChannelCount());
        writeBufferSize(os, message.getBufferSize());
        os.flush();
    }

    public static void write(DataOutputStream os, GetFramesMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_GET_FRAMES);
        writeFrameCount(os, message.getFrameCount());
        os.flush();
    }

    public static void write(DataOutputStream os, StartMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_START);
        os.flush();
    }

    public static void write(DataOutputStream os, StopMessage message) throws IOException {
        writeMessageType(os, MESSAGE_TYPE_STOP);
        os.flush();
    }

    public static Message read(DataInputStream is) throws IOException {
        final short messageType = readMessageType(is);
        switch(messageType) {
            case MESSAGE_TYPE_ACK:
                return new AckMessage();
            case MESSAGE_TYPE_NAK:
                return new NakMessage();
            case MESSAGE_TYPE_FRAMES:
                final int sampleCount = readSampleDataLength(is);
                final byte[] samples = readSampleData(is, sampleCount);
                return new FramesMessage(samples);
            case MESSAGE_TYPE_GET_FRAMES:
                int frameCount = readFrameCount(is);
                return new GetFramesMessage(frameCount);
            case MESSAGE_TYPE_INIT:
                int sampleRate = readSampleRate(is);
                short channelCount = readChannelCount(is);
                int bufferSize = readBufferSize(is);
                return new InitMessage(sampleRate, channelCount, bufferSize);
            case MESSAGE_TYPE_STOP:
                return new StopMessage();
            case MESSAGE_TYPE_START:
                return new StartMessage();
            case MESSAGE_TYPE_CLOSE:
                return new CloseMessage();
            default:
                throw new RuntimeException(String.format("Unsupported message type %s", messageType));
        }
    }

}
