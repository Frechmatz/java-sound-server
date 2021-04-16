package de.frechmatz.javasoundserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

/**
 * Prototypical reading of session initialization data.
 * Session initialization data contains information about
 * - the protocol version
 * - the binary layout and properties of messages
 * that is supported by the client.
 */
final class SessionInitialization {
    private SessionInitialization() {
    }

    private static final Logger logger =
            LogManager.getLogger(SessionInitialization.class);

    private static final int LENGTH = 128;

    private static void initImpl(InputStream is)
            throws Exception {
        byte[] input = new byte[LENGTH];
        is.read(input);
        if (input[0] != 1)
            throw new Exception(String.format(
                    "Protocol-Version %s not supported", input[0]));
        if (input[1] != 1)
            throw new Exception(String.format(
                    "Encoding-Version %s not supported", input[1]));
        for (int i = 2; i < LENGTH; i++) {
            if (input[i] != 0)
                throw new Exception(String.format(
                        "Invalid session initialization data %s", input[i]));
        }
    }

    public static void init(InputStream is)
            throws Exception {
        initImpl(is);
        logger.info("Accepted session initialization data");
    }

}
