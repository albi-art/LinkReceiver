/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver;

import my.firstApp.linkReceiver.handlers.ClientSocketsHandler;
import my.firstApp.linkReceiver.handlers.ClientSocketsHandlerImpl;
import my.firstApp.linkReceiver.handlers.ConnectionsHandler;
import my.firstApp.linkReceiver.handlers.ConnectionsHandlerImpl;
import my.firstApp.linkReceiver.handlers.IOStreamsHandler;
import my.firstApp.linkReceiver.handlers.IOStreamsHandlerImpl;
import my.firstApp.linkReceiver.handlers.MessageHandler;

public class ServerContext {

    private final ConnectionsHandler connectionsHandler;

    public ServerContext(MessageHandler messageHandler) {
        IOStreamsHandler ioStreamsHandler = new IOStreamsHandlerImpl(messageHandler);
        ClientSocketsHandler clientSocketHandler = new ClientSocketsHandlerImpl(ioStreamsHandler);
        connectionsHandler = new ConnectionsHandlerImpl(clientSocketHandler);
    }

    public ConnectionsHandler getConnectionsHandler() {
        return connectionsHandler;
    }
}
