package de.frechmatz.javasoundserver.server;

import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.message.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class JavaSoundActionHandler implements ActionHandler {
    private final MessageWriter messageWriter;
    private SourceDataLine sdl;
    private int bufferSizeFrames;
    private boolean omitAudioOutput;

    public JavaSoundActionHandler(MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
    }

    @Override
    public void init(InitMessage message) throws Exception {
        // SDL accepts a buffer size of 0
        // So for now let us validate here
        if(message.getBufferSizeFrames() <= 0)
            throw new Exception("BufferSizeFrames must be greater than 0");
        int channelCount = message.getChannelCount();
        omitAudioOutput = message.getOmitAudioOutput() == 1;
        final AudioFormat af = new AudioFormat(
                (float) message.getSampleRate(),
                message.getSampleWidth() * 8,
                channelCount,
                true,
                true);
        sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af, message.getBufferSizeFrames() * channelCount * 2);
        bufferSizeFrames = sdl.getBufferSize() / (channelCount * 2);
    }

    @Override
    public void ackInit() throws Exception {
        messageWriter.write(new AckInitMessage(this.bufferSizeFrames));
    }

    @Override
    public void requestFrames() throws Exception {
        GetFramesMessage message = new GetFramesMessage();
        messageWriter.write(message);
    }

    @Override
    public void frames(FramesMessage message) throws Exception {
        if(!omitAudioOutput) {
            // Blocking write of samples into Audio Device
            sdl.write(message.getSampleData(), 0, message.getSampleDataLength());
        }
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
