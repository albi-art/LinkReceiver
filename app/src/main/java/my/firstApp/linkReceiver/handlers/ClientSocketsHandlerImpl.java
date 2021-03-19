/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketsHandlerImpl implements ClientSocketsHandler {

    IOStreamsHandler ioStreamsHandler;

    public ClientSocketsHandlerImpl(IOStreamsHandler ioStreamsHandler) {
        this.ioStreamsHandler = ioStreamsHandler;
    }

    public void handle(Socket socket) {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream()) {
            ioStreamsHandler.handle(input, output);
        } catch (IOException e) {
            System.out.printf("Client %s%n", socket.getInetAddress() + ": " + e.getMessage());
        }
    }
}
