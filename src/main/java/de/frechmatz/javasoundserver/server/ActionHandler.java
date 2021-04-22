package de.frechmatz.javasoundserver.server;

import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.message.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class ActionHandler implements IActionHandler {
    private final MessageWriter messageWriter;
    private SourceDataLine sdl;
    private int sampleRate;

    public ActionHandler(MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void ack() throws Exception {
        messageWriter.write(new AckMessage());
    }

    @Override
    public void nak() throws Exception {
        messageWriter.write(new NakMessage());
    }

    @Override
    public void init(InitMessage message) throws Exception {
        AudioFormat af = new AudioFormat(
                (float) message.getSampleRate(),
                16,
                message.getChannelCount(),
                true,
                true);
        sdl = AudioSystem.getSourceDataLine(af);
        sdl.open();
        sampleRate = message.getSampleRate();
    }

    @Override
    public void requestFrames() throws Exception {
        final int requestedChunkLengthSeconds = 1;
        final int requestedFrameCount = sampleRate * requestedChunkLengthSeconds;
        GetFramesMessage message = new GetFramesMessage(requestedFrameCount);
        messageWriter.write(message);
    }

    @Override
    public void frames(FramesMessage message) throws Exception {
        // Blocking write of samples into Audio Device
        sdl.write(message.getSampleData(), 0, message.getSampleData().length);
    }

    @Override
    public void start() throws Exception {
        sdl.start();
    }

    @Override
    public void stop() throws Exception {
        sdl.stop();
    }

    @Override
    public void close() throws Exception {
        if (sdl != null) {
            SourceDataLine buf = sdl;
            sdl = null;
            buf.close();
        }
    }
}
