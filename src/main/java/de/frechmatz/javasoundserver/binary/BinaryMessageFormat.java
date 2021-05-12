package de.frechmatz.javasoundserver.binary;

import de.frechmatz.javasoundserver.api.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Holds a sample buffer that is shared across
 * multiple instances of FramesMessage.
 */
final class BinaryMessageFormat {

    private final DataInputStream is;
    private final DataOutputStream os;
    private byte[] sampleBuffer = new byte[0];

    public BinaryMessageFormat(DataInputStream is, DataOutputStream os) {
        this.is = is;
        this.os = os;
    }

    private short readMessageType() throws IOException {
        return is.readShort();
    }

    private void writeMessageType(short commandId) throws IOException {
        os.writeShort(commandId);
    }

    private short readOmitAudioOutput() throws IOException {
        return is.readShort();
    }

    private void writeOmitAudioOutput(short omitAudioOutput)
            throws IOException {
        os.writeShort(omitAudioOutput);
    }

    private int readSampleDataLength() throws IOException {
        return is.readInt();
    }

    private void writeSampleDataLength(int frameCount) throws IOException {
        os.writeInt(frameCount);
    }

    /**
     * Get a buffer into which samples are to be read.
     * Buffer is shared across multiple read calls.
     * @param sampleDataLength
     * @return
     */
    private byte[] getSampleBuffer(int sampleDataLength) {
        if (sampleBuffer.length < sampleDataLength)
            sampleBuffer = new byte[sampleDataLength];
        return sampleBuffer;
    }

    private byte[] readSampleData(int sampleDataLength) throws IOException {
        byte[] arr = getSampleBuffer(sampleDataLength);
        is.readFully(arr, 0, sampleDataLength);
        return arr;
    }

    private void writeShortSamples(byte[] samples, int sampleDataLength) throws IOException {
        throw new RuntimeException("writeShortSamples not implemented");
    }

    private int readSampleRate() throws IOException {
        return is.readInt();
    }

    private void writeSampleRate(int sampleRate) throws IOException {
        os.writeInt(sampleRate);
    }

    private short readSampleWidth() throws IOException {
        return is.readShort();
    }

    private void writeSampleWidth(short sampleWidth) throws IOException {
        os.writeShort(sampleWidth);
    }

    private short readChannelCount() throws IOException {
        return is.readShort();
    }

    private void writeChannelCount(short channelCount) throws IOException {
        os.writeShort(channelCount);
    }

    private int readBufferSizeFrames() throws IOException {
        return is.readInt();
    }

    private void writeBufferSizeFrames(int bufferSize) throws IOException {
        os.writeInt(bufferSize);
    }

    private static final short MESSAGE_TYPE_FRAMES = 3;
    private static final short MESSAGE_TYPE_GET_FRAMES = 4;
    private static final short MESSAGE_TYPE_INIT = 5;
    private static final short MESSAGE_TYPE_STOP = 6;
    private static final short MESSAGE_TYPE_START = 7;
    private static final short MESSAGE_TYPE_CLOSE = 8;
    private static final short MESSAGE_TYPE_INITACK = 9;

    public void write(CloseMessage message) throws IOException {
        writeMessageType(MESSAGE_TYPE_CLOSE);
        os.flush();
    }

    public void write(FramesMessage message) throws IOException {
        writeMessageType(MESSAGE_TYPE_FRAMES);
        writeSampleDataLength(message.getSampleDataLength());
        writeShortSamples(message.getSampleData(), message.getSampleDataLength());
        os.flush();
    }

    public void write(InitMessage message) throws IOException {
        writeMessageType(MESSAGE_TYPE_INIT);
        writeSampleRate(message.getSampleRate());
        writeSampleWidth(message.getSampleWidth());
        writeChannelCount(message.getChannelCount());
        writeBufferSizeFrames(message.getBufferSizeFrames());
        writeOmitAudioOutput(message.getOmitAudioOutput());
        os.flush();
    }

    public void write(AckInitMessage message) throws IOException {
        writeMessageType(MESSAGE_TYPE_INITACK);
        writeBufferSizeFrames(message.getBufferSizeFrames());
        os.flush();
    }

    public void write(GetFramesMessage message) throws IOException {
        writeMessageType(MESSAGE_TYPE_GET_FRAMES);
        os.flush();
    }

    public void write(StartMessage message) throws IOException {
        writeMessageType(MESSAGE_TYPE_START);
        os.flush();
    }

    public void write(StopMessage message) throws IOException {
        writeMessageType(MESSAGE_TYPE_STOP);
        os.flush();
    }

    public Message read() throws IOException {
        final short messageType = readMessageType();
        switch(messageType) {
            case MESSAGE_TYPE_FRAMES:
                final int sampleDataLength = readSampleDataLength();
                final byte[] samples = readSampleData(sampleDataLength);
                return new FramesMessage(sampleDataLength, samples);
            case MESSAGE_TYPE_GET_FRAMES:
                return new GetFramesMessage();
            case MESSAGE_TYPE_INIT:
                int sampleRate = readSampleRate();
                short sampleWidth = readSampleWidth();
                short channelCount = readChannelCount();
                int bufferSize = readBufferSizeFrames();
                short omitAudioOutput = readOmitAudioOutput();
                return new InitMessage(sampleRate, sampleWidth,
                        channelCount, bufferSize, omitAudioOutput);
            case MESSAGE_TYPE_INITACK:
                int bufferSizeFrames = readBufferSizeFrames();
                return new AckInitMessage(bufferSizeFrames);
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
