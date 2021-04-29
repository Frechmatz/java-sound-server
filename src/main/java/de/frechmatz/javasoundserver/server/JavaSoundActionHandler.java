package de.frechmatz.javasoundserver.server;

import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.message.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class JavaSoundActionHandler implements ActionHandler {
    private final MessageWriter messageWriter;
    private SourceDataLine sdl;
    private int sampleRate;
    private int bufferSizeFrames;
    private int channelCount;
    private int sampleSizeWidth;
    private boolean omitAudioOutput;

    public JavaSoundActionHandler(MessageWriter messageWriter) {
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
        if(message.getBufferSizeFrames() <= 0)
            throw new Exception("BufferSizeFrames must be greater than 0");
        if(message.getSampleWidth() != 2)
            throw new Exception(String.format(
                    "SampleWidth of %s not supported.",
                    message.getSampleWidth()));
        channelCount = message.getChannelCount();
        sampleSizeWidth = 16;
        sampleRate = message.getSampleRate();
        omitAudioOutput = message.getOmitAudioOutput() == 1 ? true : false;
        final AudioFormat af = new AudioFormat(
                (float) sampleRate,
                sampleSizeWidth,
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
            sdl.write(message.getSampleData(), 0, message.getSampleData().length);
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
