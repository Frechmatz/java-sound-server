package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.api.Message;

public class InitMessage implements Message {

    private final int sampleRate;
    private final short channelCount;
    private final int bufferSize;

    public InitMessage(int sampleRate, short channelCount, int bufferSize) {
        this.sampleRate = sampleRate;
        this.channelCount = channelCount;
        this.bufferSize = bufferSize;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public short getChannelCount() {
        return channelCount;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "InitMessage{" +
                "sampleRate=" + sampleRate +
                ", channelCount=" + channelCount +
                ", bufferSize=" + bufferSize +
                '}';
    }
}
