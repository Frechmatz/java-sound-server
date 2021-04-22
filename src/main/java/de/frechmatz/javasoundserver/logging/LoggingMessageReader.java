package de.frechmatz.javasoundserver.logging;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logs messages successfully read from stream
 * Does not log any errors.
 */
public class LoggingMessageReader implements MessageReader {
    private static final Logger logger = LogManager.getLogger(LoggingMessageReader.class);

    private final MessageReader reader;

    public LoggingMessageReader(MessageReader reader) {
        this.reader = reader;
    }

    @Override
    public Message read() throws Exception {
        Message message = reader.read();
        logger.info("Inbound: {}", message);
        return message;
    }
}
