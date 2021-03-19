/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import my.firstApp.linkReceiver.threads.Connection;

public class ConnectionsHandlerImpl implements ConnectionsHandler {

    private final ClientSocketsHandler clientSocketsHandler;

    public ConnectionsHandlerImpl(ClientSocketsHandler clientSocketHandler) {
        this.clientSocketsHandler = clientSocketHandler;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void handle(ServerSocket serverSocket) throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Connection connection = new Connection(socket, clientSocketsHandler);
            connection.start();
        }
    }
}
