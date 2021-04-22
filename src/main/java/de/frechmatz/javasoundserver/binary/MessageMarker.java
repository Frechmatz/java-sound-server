package de.frechmatz.javasoundserver.binary;

import java.io.DataInputStream;
import java.io.DataOutputStream;

final class MessageMarker {

    private static final byte[] startOfMessageMarker = {1,2,3,4};
    private static final byte[] endOfMessageMarker = {4,3,2,1};

    private static void validateMarker(DataInputStream is, byte[] marker)
        throws Exception {
        for(int i = 0; i < marker.length; i++) {
            byte b = is.readByte();
            if (b != marker[i])
                throw new Exception("Invalid Start-Of-Message marker");
        }
    }

    private static void writeMarker(DataOutputStream os, byte[] marker)
            throws Exception {
        for(int i = 0; i < marker.length; i++) {
            os.writeByte(marker[i]);
        }
    }

    static void readStart(DataInputStream is) throws Exception {
        validateMarker(is, startOfMessageMarker);
    }

    static void readEnd(DataInputStream is) throws Exception {
        validateMarker(is, endOfMessageMarker);
    }

    static void writeStart(DataOutputStream os) throws Exception {
        writeMarker(os, startOfMessageMarker);
    }

    static void writeEnd(DataOutputStream os) throws Exception {
        writeMarker(os, endOfMessageMarker);
    }
}
