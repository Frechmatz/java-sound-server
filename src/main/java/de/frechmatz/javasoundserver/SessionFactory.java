package de.frechmatz.javasoundserver;

import java.io.InputStream;

final class SessionFactory {

    private SessionFactory() {
    }

    private static final int LENGTH = 16;

    public static Session instantiate(InputStream is)
            throws Exception {
        byte[] data = is.readNBytes(LENGTH);
        int major = data[0];
        int minor = data[1];
        int patch = data[2];
        for( int i = 3; i < LENGTH; i++) {
            if (data[i] != 0) {
                throw new Exception(
                           "Invalid session initialization data: " +
                                    data.toString());
            }
        }
        return new Session(major,minor,patch);
    }

}
