package de.frechmatz.javasoundserver.logging;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.message.*;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public void write(AckMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void write(NakMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void write(CloseMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void write(FramesMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void write(InitMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void write(GetFramesMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void write(StartMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void write(StopMessage message) throws IOException {
        try {
            writer.write(message);
            logMessage(message);
        } catch(IOException error) {
            logger.error(error);
            throw error;
        }
    }
}
