package de.frechmatz.javasoundserver.api;

public class AckInitMessage implements Message {
    private final int bufferSizeFrames;

    public AckInitMessage(int bufferSizeFrames) {
        this.bufferSizeFrames = bufferSizeFrames;
    }

    public int getBufferSizeFrames() {
        return bufferSizeFrames;
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "InitAckMessage{" +
                "bufferSizeFrames=" + bufferSizeFrames +
                '}';
    }
}
