package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageHandler;

public class FramesMessage implements Message {
    private final byte[] sampleData;
    private final int sampleDataLength;

    /**
     * @param sampleData
     * @param sampleDataLength
     */
    public FramesMessage(int sampleDataLength, byte[] sampleData) {
        this.sampleData = sampleData;
        this.sampleDataLength = sampleDataLength;
    }

    public byte[] getSampleData() {
        return sampleData;
    }

    public int getSampleDataLength() {
        return sampleDataLength;
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "FramesMessage{" +
                "sampleData=[]"  +
                ", sampleDataLength=" + sampleDataLength +
                '}';
    }
}
