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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        return args -> {
            ServerSocket serverSocket = null;
            try {
                logger.info("Starting socket server");
                serverSocket = new ServerSocket(9000);
                while (true) {
                    logger.info("Accepting connection...");
                    Socket socket = serverSocket.accept();
                    logger.info("Connection established");
                    try {
                        SessionInitialization.init(socket.getInputStream());
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
            } catch (Exception error) {
                logger.error("Closing server", error);
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException closeError) {
                        logger.error(closeError);
                    }
                }
            }
        };
    }

}
