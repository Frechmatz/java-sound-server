package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.api.Message;

public class InitMessage implements Message {

    private final int sampleRate;
    private final short channelCount;
    private final int bufferSizeFrames;
    private final short omitAudioOutput;

    public InitMessage(int sampleRate, short channelCount, int bufferSizeFrames,
                       short omitAudioOutput) {
        this.sampleRate = sampleRate;
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

    @Override
    public String toString() {
        return "InitMessage{" +
                "sampleRate=" + sampleRate +
                ", channelCount=" + channelCount +
                ", bufferSizeFrames=" + bufferSizeFrames +
                ", omitAudioOutput=" + omitAudioOutput +
                '}';
    }
}
