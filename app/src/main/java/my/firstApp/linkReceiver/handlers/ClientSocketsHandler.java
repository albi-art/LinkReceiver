/*
 * Copyright (c) 2021.
 * https://github.com/albi-art
 */

package my.firstApp.linkReceiver.handlers;

import java.net.Socket;

public interface ClientSocketsHandler {
    void handle(Socket socket);
}
