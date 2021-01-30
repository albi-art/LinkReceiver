/*
 * Copyright (c) 2021.
 * https://github.com/albi-art
 */

package my.firstApp.linkReceiver.threads;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import my.firstApp.linkReceiver.ServerContext;
import my.firstApp.linkReceiver.handlers.MessageHandler;

/**
 * The class of threads handling server socket connections
 */
public class Server extends Thread {
    private final ServerContext serverContext;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Server(MessageHandler messageHandler) {
        serverContext = new ServerContext(messageHandler);
    }

    @Override
    public void run() {
        super.run();
        int SERVER_PORT = 42000;
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.printf("Server is listening on port %d", SERVER_PORT);
            serverContext.getConnectionsHandler().handle(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getFirstInterfaceIPv4() {
        try {
            return getIPv4AddressList().get(0);
        } catch (SocketException e) {
            e.printStackTrace();
            return "0.0.0.0";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static ArrayList<String> getIPv4AddressList() throws SocketException {
        final String LOOP_IP_REGEXP = "(127.0.0.1)|(0.0.0.0)";
        final String IP_V6_REGEXP = "(.*::.*)";
        final String FILTER_REGEXP = LOOP_IP_REGEXP + "|" + IP_V6_REGEXP;

        ArrayList<NetworkInterface> interfaces = Collections
                .list(NetworkInterface.getNetworkInterfaces());

        return (ArrayList<String>) interfaces.stream()
                .flatMap(i -> Collections.list(i.getInetAddresses()).stream()
                        .map(InetAddress::getHostAddress))
                .filter(ip -> !ip.matches(FILTER_REGEXP))
                .collect(Collectors.toList());
    }
}
