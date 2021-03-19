/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.handlers;

import java.net.Socket;

public interface ClientSocketsHandler {
    void handle(Socket socket);
}
