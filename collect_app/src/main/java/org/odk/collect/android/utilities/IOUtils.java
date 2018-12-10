package org.odk.collect.android.utilities;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Selector;

public class IOUtils {
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    public static void closeQuietly(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    public static void closeQuietly(final InputStream input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(final OutputStream output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(final Reader input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(final Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }

    public static void closeQuietly(final ServerSocket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }

    public static void closeQuietly(final Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }

    public static void closeQuietly(final Writer output) {
        closeQuietly((Closeable) output);
    }

    private static final int BUFFER_SIZE = 1024 * 1024 * 30;

    public static void copy(final InputStream inputStream, final OutputStream outputStream) {
        try {
            int read;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((read = inputStream.read(buffer, 0, BUFFER_SIZE)) > 0) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
        } catch (IOException ex) {
            //ignore
        } finally {
            try {
                outputStream.flush();
            } catch (IOException e) {
                //ignore
            }
        }
    }
}