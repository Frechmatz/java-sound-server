package de.frechmatz.javasoundserver.server;

import de.frechmatz.javasoundserver.api.MessageHandler;
import de.frechmatz.javasoundserver.message.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implements the server side of the communication protocol between client and server.
 */
public class ProtocolHandler implements MessageHandler {
    private static final Logger logger = LogManager.getLogger(ProtocolHandler.class);

    private final ActionHandler actionHandler;

    private enum State {
        AWAITING_INIT,
        AWAITING_START,
        AWAITING_FRAMES,
        CLOSED
    }

    @Override
    public String toString() {
        return "ProtocolHandler{" +
                "state=" + state +
                '}';
    }

    private State state = State.AWAITING_INIT;

    public ProtocolHandler(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    private void close() {
        if (state == State.CLOSED)
            return;
        state = State.CLOSED;
        try {
            actionHandler.close();
        } catch (Exception error) {
            // Ignore
        }
    }

    @Override
    public void onMessage(AckMessage message) throws Exception {
        close();
        throw new ProtocolException(message);
    }

    @Override
    public void onMessage(NakMessage message) throws Exception {
        close();
        throw new ProtocolException(message);
    }

    @Override
    public void onMessage(AckInitMessage message) throws Exception {
        close();
        throw new ProtocolException(message);
    }

    @Override
    public void onMessage(GetFramesMessage message) throws Exception {
        close();
        throw new ProtocolException(message);
    }

    @Override
    public void onMessage(CloseMessage message) throws Exception {
        switch (state) {
            case CLOSED:
                throw new ProtocolException(message);
            default:
                close();
                throw new DoneException();
        }
    }

    @Override
    public void onMessage(FramesMessage message) throws Exception {
        switch (state) {
            case AWAITING_FRAMES:
                try {
                    actionHandler.frames(message);
                    actionHandler.requestFrames();
                    // remain in current state
                } catch (Exception error) {
                    close();
                    throw error;
                }
                break;
            default:
                close();
                throw new ProtocolException(message);
        }
        logger.debug(toString());
    }

    @Override
    public void onMessage(InitMessage message) throws Exception {
        switch (state) {
            case AWAITING_INIT:
                try {
                    actionHandler.init(message);
                    actionHandler.ackInit();
                    state = State.AWAITING_START;
                } catch (Exception error) {
                    close();
                    throw error;
                }
                break;
            default:
                close();
                throw new ProtocolException(message);
        }
        logger.debug(toString());
    }

    @Override
    public void onMessage(StartMessage message) throws Exception {
        switch (state) {
            case AWAITING_START:
                try {
                    actionHandler.start();
                    actionHandler.requestFrames();
                    state = State.AWAITING_FRAMES;
                } catch (Exception error) {
                    close();
                    throw error;
                }
                break;
            default:
                close();
                throw new ProtocolException(message);
        }
        logger.debug(toString());
    }

    @Override
    public void onMessage(StopMessage message) throws Exception {
        switch (state) {
            case AWAITING_FRAMES:
                try {
                    actionHandler.stop();
                    state = State.AWAITING_START;
                } catch (Exception error) {
                    close();
                    throw error;
                }
                break;
            default:
                close();
                throw new ProtocolException(message);
        }
        logger.debug(toString());
    }
}
