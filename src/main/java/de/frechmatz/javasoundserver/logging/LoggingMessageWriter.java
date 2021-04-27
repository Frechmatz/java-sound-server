package de.frechmatz.javasoundserver.logging;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.message.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logs messages successfully written into stream.
 * Does not log any errors.
 */
public class LoggingMessageWriter implements MessageWriter {
    private static final Logger logger = LogManager.getLogger(LoggingMessageWriter.class);
    private final MessageWriter writer;


    public LoggingMessageWriter(MessageWriter writer) {
        this.writer = writer;
    }

    private void logMessage(final Message message) {
        logger.info("Outbound: {}", message);
    }

    @Override
    public void write(AckMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(NakMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(CloseMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(FramesMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(InitMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(AckInitMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(GetFramesMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(StartMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }

    @Override
    public void write(StopMessage message) throws Exception {
        writer.write(message);
        logMessage(message);
    }
}
