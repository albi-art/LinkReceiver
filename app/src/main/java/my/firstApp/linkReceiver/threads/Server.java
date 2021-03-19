/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.threads;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.ServerSocket;

import my.firstApp.linkReceiver.ServerContext;
import my.firstApp.linkReceiver.handlers.MessageHandler;
import my.firstApp.linkReceiver.services.IPv4Service;
import my.firstApp.linkReceiver.services.NetworkService;

/**
 * The class of threads handling server socket connections
 */
public class Server extends Thread {
    private final ServerContext serverContext;
    private final NetworkService networkService = new IPv4Service();

    private static Server INSTANCE;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Server getInstance(MessageHandler messageHandler) {
        if (INSTANCE == null) {
            INSTANCE = new Server(messageHandler);
        }

        return INSTANCE;
    }

    private Server(MessageHandler messageHandler) {
        serverContext = new ServerContext(messageHandler);
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    @Override
    public void run() {
        super.run();
        int SERVER_PORT = 42000;
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.printf("Server is listening on port %d%n", SERVER_PORT);
            serverContext.getConnectionsHandler().handle(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
