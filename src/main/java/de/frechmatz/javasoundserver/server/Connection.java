package de.frechmatz.javasoundserver.server;

import de.frechmatz.javasoundserver.api.Message;
import de.frechmatz.javasoundserver.api.MessageReader;
import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.logging.LoggingActionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Connection {
    private static final Logger logger = LogManager.getLogger(Connection.class);

    private final MessageWriter messageWriter;
    private final MessageReader messageReader;

    public Connection(MessageReader messageReader, MessageWriter messageWriter) {
        this.messageWriter = messageWriter;
        this.messageReader = messageReader;
    }

    public void run() {
        de.frechmatz.javasoundserver.api.ActionHandler actionHandler = new LoggingActionHandler(
                new ActionHandler(messageWriter));
        ProtocolHandler protocolHandler = new ProtocolHandler(actionHandler);
        logger.info(protocolHandler.toString());
        try {
            while (true) {
                Message message = messageReader.read();
                message.dispatch(protocolHandler);
                logger.info(protocolHandler.toString());
            }
        } catch(DoneException done) {
            // Nothing to do here. Protocol handler
            // has signalled that everything is fine
            // and the connection can now be closed :)
            logger.info("Closing connection");
        } catch(Exception error) {
            logger.error("ProtocolHandler has signalled an error. Closing connection",error);
        }
    }
}
