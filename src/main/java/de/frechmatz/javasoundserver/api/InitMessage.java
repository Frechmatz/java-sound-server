package de.frechmatz.javasoundserver.api;

public class InitMessage implements Message {

    private final int sampleRate;
    private final short sampleWidth;
    private final short channelCount;
    private final int bufferSizeFrames;
    private final short omitAudioOutput;

    public InitMessage(int sampleRate, short sampleWidth, short channelCount, int bufferSizeFrames,
                       short omitAudioOutput) {
        this.sampleRate = sampleRate;
        this.sampleWidth = sampleWidth;
        this.channelCount = channelCount;
        this.bufferSizeFrames = bufferSizeFrames;
        this.omitAudioOutput = omitAudioOutput;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public short getChannelCount() {
        return channelCount;
    }

    public int getBufferSizeFrames() {
        return bufferSizeFrames;
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    public short getOmitAudioOutput() {
        return omitAudioOutput;
    }

    public short getSampleWidth() {
        return sampleWidth;
    }

    @Override
    public String toString() {
        return "InitMessage{" +
                "sampleRate=" + sampleRate +
                ", sampleWidth=" + sampleWidth +
                ", channelCount=" + channelCount +
                ", bufferSizeFrames=" + bufferSizeFrames +
                ", omitAudioOutput=" + omitAudioOutput +
                '}';
    }
}
