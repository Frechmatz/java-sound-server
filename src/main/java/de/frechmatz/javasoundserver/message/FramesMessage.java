package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.api.Message;

public class FramesMessage implements Message {
    private final int sampleCount;
    private final byte[] samples;

    /**
     * Why sampleCount instead of frameCount: frameCount
     * would require a context (the number of channels)
     * without the message could not be read.
     * @param sampleCount
     * @param samples
     */
    public FramesMessage(int sampleCount, byte[] samples) {
        this.sampleCount = sampleCount;
        this.samples = samples;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public byte[] getSamples() {
        return samples;
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "FramesMessage{" +
                "sampleCount=" + sampleCount +
                ", samples=[...] (length=" + samples.length + ")}";
    }
}
