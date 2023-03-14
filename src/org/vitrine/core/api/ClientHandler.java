package org.vitrine.core.api;

import org.vitrine.core.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        clientSocket = socket;
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String input;
        while (true) {
            try {
                if ((input = in.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Console.println(input, Console.Color.RED);
            out.println(input);
            /*
            @TODO out.println(parseApi(input));
             */
        }

        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
