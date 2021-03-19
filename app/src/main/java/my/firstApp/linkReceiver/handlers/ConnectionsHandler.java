/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.handlers;

import java.io.IOException;
import java.net.ServerSocket;

public interface ConnectionsHandler {
    void handle(ServerSocket serverSocket) throws IOException;
}
