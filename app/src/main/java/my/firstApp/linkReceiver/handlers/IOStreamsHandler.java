/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStreamsHandler {
    void handle(InputStream input, OutputStream output) throws IOException;
}
