package org.packman.client.socket;

import lombok.RequiredArgsConstructor;
import org.packman.client.services.DrawService;
import org.packman.client.services.impl.DrawServiceImpl;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import static org.packman.client.utils.PropertiesUtil.*;

//todo log
@RequiredArgsConstructor
public class ClientSocket {
    private static final DrawService drawService = new DrawServiceImpl();
    private static PrintWriter out;
    private static BufferedReader in;
    private static Socket socket;

    public static void connection() throws Exception {
        String SERVER_ADDRESS = getIP();
        int PORT = getPort();
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            drawService.drawMenu();
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Ваш метод с бесконечным циклом
                    while (!socket.isClosed()) {
                    }
                    throw new SocketException();
                }
            };

            worker.execute();

        } catch (IOException e) {
            throw new Exception();
        }
    }

    public static String sendCommand(String command, String username) {
        out.println(command + " " + username);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();//todo add ex
        }
    }

    public static void quit() {
        try {
            socket.close();
            in.close();
            out.close();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public static String sendCommand(String command) {
        out.println(command);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();//todo add ex
        }
    }
}
