package com.blogspot.qbeukes.cubes.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: quintin
 * Date: 22 Nov 2010
 * Time: 12:27:57 AM
 */
public class StreamUtils {
  private static final int BUF_SIZE = 128;

  /**
   * Read data from an input stream into an output stream.
   * <p>
   * Streams will not be closed when done.
   *
   * @param inputStream
   * @param outputStream
   * @throws IOException If an IO error occurs
   * @throws IllegalArgumentException If either supplied streams are null.
   */
  public static void transferStream(InputStream inputStream, OutputStream outputStream) throws IOException {
    if (inputStream == null) {
      throw new IllegalArgumentException("Input stream may not be null.");
    }

    if (outputStream == null) {
      throw new IllegalArgumentException("Output stream may not be null.");
    }

    byte[] buf = new byte[BUF_SIZE];

    int read;
    while ((read = inputStream.read(buf)) != -1) {
      outputStream.write(buf, 0, read);
    }
  }
}
