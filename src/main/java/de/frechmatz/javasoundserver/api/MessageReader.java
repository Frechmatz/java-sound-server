package de.frechmatz.javasoundserver.api;

import java.io.IOException;

public interface MessageReader {
    Message read() throws IOException;
}
