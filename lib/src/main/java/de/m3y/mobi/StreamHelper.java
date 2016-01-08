package de.m3y.mobi;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Helper for working with streams.
 */
public class StreamHelper {

    private StreamHelper() {
        // Not instantiable
    }

    /**
     * Reads a string till either number of bytes reached, or 0x0 reached.
     *
     * @param is            the input stream.
     * @param numberOfBytes the maximum number of bytes.
     * @return string read from stream.
     * @throws IOException on io error.
     */
    public static String readStringTillNull(DataInputStream is, int numberOfBytes) throws IOException {
        byte[] b = new byte[numberOfBytes];
        is.readFully(b);
        // Find numberOfBytes depending on first 0x0 byte
        int len = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] == 0x0) {
                len = i;
                break;
            }
        }
        return new String(b, 0, len);
    }

    /**
     * Reads a string from the stream of given length.
     *
     * @param is            the input stream.
     * @param numberOfBytes the number of bytes.
     * @return the string read from the stream.
     * @throws IOException on io error.
     */
    public static String readString(DataInputStream is, int numberOfBytes) throws IOException {
        byte[] b = new byte[numberOfBytes];
        is.readFully(b);
        return new String(b);
    }
}
