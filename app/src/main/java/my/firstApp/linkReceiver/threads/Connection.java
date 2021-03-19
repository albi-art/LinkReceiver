/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.threads;

import java.net.Socket;

import my.firstApp.linkReceiver.handlers.ClientSocketsHandler;

/**
 * Class of threads handling one client connection
 */
public class Connection extends Thread {

    private final Socket clientSocket;
    private final ClientSocketsHandler clientSocketsHandler;

    public Connection(Socket clientSocket, ClientSocketsHandler clientSocketsHandler) {
        this.clientSocket = clientSocket;
        this.clientSocketsHandler = clientSocketsHandler;
    }

    @Override
    public void run() {
        super.run();
        System.out.printf("New client connected: %s%n", clientSocket.getInetAddress());
        clientSocketsHandler.handle(clientSocket);
    }
}
