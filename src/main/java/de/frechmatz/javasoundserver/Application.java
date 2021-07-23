package de.frechmatz.javasoundserver;

import de.frechmatz.javasoundserver.api.MessageReader;
import de.frechmatz.javasoundserver.api.MessageWriter;
import de.frechmatz.javasoundserver.binary.BinaryMessageReader;
import de.frechmatz.javasoundserver.binary.BinaryMessageWriter;
import de.frechmatz.javasoundserver.logging.LoggingMessageReader;
import de.frechmatz.javasoundserver.logging.LoggingMessageWriter;
import de.frechmatz.javasoundserver.server.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
        final int port = args.length > 0 ? Integer.parseInt(args[0]) : 9000;
        final ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            logger.info(String.format("Accepting connection on port %s...", port));
            Socket socket = serverSocket.accept();
            logger.info("Connection established");
            try {
                final Session session = SessionFactory.instantiate(socket.getInputStream());
                if (!(session.getMajorVersion() == 1 && session.getMinorVersion() == 0))
                    throw new Exception(String.format("Version %s.%s not supported",
                            session.getMajorVersion(),
                            session.getMinorVersion()));
                logger.info("Accepted " + session);
                socket.getOutputStream().write(0); // Accepted
                socket.getOutputStream().flush();
                MessageReader reader = new LoggingMessageReader(
                        new BinaryMessageReader(
                                new DataInputStream(socket.getInputStream())));
                MessageWriter writer = new LoggingMessageWriter(
                        new BinaryMessageWriter(
                                new DataOutputStream(socket.getOutputStream())));
                Connection connection = new Connection(reader, writer);
                connection.run();
                socket.close();
            } catch (Exception error) {
                logger.error(error);
                try {
                    socket.close();
                } catch (IOException closeError) {
                    logger.error(closeError);
                }
            }
        }
    }
}
