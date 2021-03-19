/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import my.firstApp.linkReceiver.handlers.MessageHandler;
import my.firstApp.linkReceiver.threads.Server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    Server server = Server.getInstance(new MessageHandlerMock());
    final int SERVER_PORT = 42000;
    static final String TEST_MESSAGE = "test message";

    static class MessageHandlerMock implements MessageHandler {
        @Override
        public void handle(String message) {
            assertEquals(TEST_MESSAGE, message);
        }
    }

    @Test
    public void serverRun_isCorrect() throws UnknownHostException {
        server.start();
        String ip = Inet4Address.getLocalHost().getHostAddress();
        boolean isConnected = false;
        try (Socket clientSocket = new Socket(ip, SERVER_PORT)) {
            isConnected = true;
        } catch (IOException e) {
            isConnected = false;
        } finally {
            assertTrue(isConnected);
        }
    }

    @Test
    public void serverMessageHandle_isCorrect() throws IOException {
        server.start();
        String ip = Inet4Address.getLocalHost().getHostAddress();
        try (Socket clientSocket = new Socket(ip, SERVER_PORT);
             OutputStream outputStream = clientSocket.getOutputStream();
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            out.write(TEST_MESSAGE);
            out.flush();
        }
    }
}
