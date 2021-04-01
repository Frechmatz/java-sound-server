package de.frechmatz.javasoundserver.logging;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LoggingMessageReader implements MessageReader {
    private static final Logger logger = LogManager.getLogger(LoggingMessageReader.class);

    private final MessageReader reader;

    public LoggingMessageReader(MessageReader reader) {
        this.reader = reader;
    }

    @Override
    public Message read() throws IOException {
        try {
            Message message = reader.read();
            logger.info("Inbound: {}", message);
            return message;
        } catch(IOException error){
            logger.error(error);
            throw error;
        }
    }
}
