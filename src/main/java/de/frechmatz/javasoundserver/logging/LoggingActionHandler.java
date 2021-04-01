package de.frechmatz.javasoundserver.logging;

import de.frechmatz.javasoundserver.api.ActionHandler;
import de.frechmatz.javasoundserver.message.FramesMessage;
import de.frechmatz.javasoundserver.message.InitMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingActionHandler implements ActionHandler {
    private static final Logger logger = LogManager.getLogger(LoggingActionHandler.class);

    private final ActionHandler handler;

    public LoggingActionHandler(ActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void ack() throws Exception {
        try {
            handler.ack();
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void nak() throws Exception {
        try {
            handler.nak();
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void init(InitMessage message) throws Exception {
        try {
            handler.init(message);
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void requestFrames() throws Exception {
        try {
            handler.requestFrames();
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void frames(FramesMessage message) throws Exception {
        try {
            handler.frames(message);
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void start() throws Exception {
        try {
            handler.start();
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void stop() throws Exception {
        try {
            handler.stop();
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }

    @Override
    public void close() throws Exception {
        try {
            handler.close();
        } catch(Exception error) {
            logger.error(error);
            throw error;
        }
    }
}
