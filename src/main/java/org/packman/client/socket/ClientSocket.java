package org.packman.client.socket;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@RequiredArgsConstructor
public class ClientSocket implements ClientCommand {
    private static String SERVER_ADDRESS;
    static {
        try {
            SERVER_ADDRESS = InetAddress.getLocalHost().getHostAddress();//todo
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static final int PORT = 2020;//todo
    private static PrintWriter out;
    private static BufferedReader in;

    public static void connection() {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sendCommand(String command, String username) {
        out.println(command + " " + username);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();//todo
        }
    }

    @Override
    public String sendCommand(String command) {
        out.println(command);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();//todo
        }
    }
}
