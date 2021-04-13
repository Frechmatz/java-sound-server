package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.api.Message;

public class FramesMessage implements Message {
    private final byte[] sampleData;

    /**
     * @param sampleData
     */
    public FramesMessage(byte[] sampleData) {
        this.sampleData = sampleData;
    }

    public byte[] getSampleData() {
        return sampleData;
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "FramesMessage{" +
                "samples=[...] (length=" + sampleData.length + ")}";
    }
}
