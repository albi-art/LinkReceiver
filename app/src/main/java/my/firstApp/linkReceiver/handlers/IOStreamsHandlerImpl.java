/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */

package my.firstApp.linkReceiver.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class IOStreamsHandlerImpl implements IOStreamsHandler {

    private final MessageHandler messageHandler;

    public IOStreamsHandlerImpl(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void handle(InputStream input, OutputStream output) throws IOException {
        while (true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(output));

            String clientMessage = in.readLine();
            if (clientMessage == null) {
                continue;
            }
            synchronized (this) {
                messageHandler.handle(clientMessage);
            }
            out.write(clientMessage + "\n");
            out.flush();
            System.out.printf("Client > %s%n", clientMessage);
        }
    }
}
