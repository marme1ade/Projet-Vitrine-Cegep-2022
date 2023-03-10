package org.vitrine.core.api;

import org.vitrine.core.Console;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread {
    private int port;
    private ServerSocket serverSocket;

    public Server (int port) {
        this.port = port;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while (!serverSocket.isClosed()) {
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void close() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
