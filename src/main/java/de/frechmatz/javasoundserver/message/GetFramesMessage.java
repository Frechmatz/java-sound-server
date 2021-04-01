package de.frechmatz.javasoundserver.message;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.api.Message;

public class GetFramesMessage implements Message {
    private final int frameCount;

    public GetFramesMessage(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getFrameCount() {
        return frameCount;
    }

    @Override
    public void dispatch(MessageHandler handler) throws Exception {
        handler.onMessage(this);
    }

    @Override
    public String toString() {
        return "GetFramesMessage{" +
                "frameCount=" + frameCount +
                '}';
    }
}
