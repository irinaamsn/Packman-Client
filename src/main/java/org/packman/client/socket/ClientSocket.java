package org.packman.client.socket;

import lombok.RequiredArgsConstructor;
import org.packman.client.services.DrawService;
import org.packman.client.services.impl.DrawServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import static org.packman.client.utils.PropertiesUtil.*;

@RequiredArgsConstructor
public class ClientSocket {
    private static final Logger logger = LoggerFactory.getLogger(ClientSocket.class);
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
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    while (!socket.isClosed()) {
                    }
                    throw new SocketException();//todo ex
                }
            };

            worker.execute();

        } catch (IOException e) {
            throw new Exception();
        }
    }

    public static String sendCommand(String command, String username) {
        System.out.println("Отправлена команда серверу: " + command + " " + username);
        out.println(command + " " + username);
        try {
            String response = in.readLine();
            System.out.println("Получен ответ от сервера: " + response);
            return response;
        } catch (IOException e) {
            throw new RuntimeException();//todo add ex
        }
    }

    public static void quit() {
        try {
            System.out.println("Закрыто соединение с сервером");
            socket.close();
            in.close();
            out.close();
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    public static String sendCommand(String command) {
        System.out.println("Отправлена команда серверу: " + command);
        out.println(command);
        try {
            String response = in.readLine();
            System.out.println("Получен ответ от сервера: " + response);
            return response;
        } catch (IOException e) {
            throw new RuntimeException();//todo add ex
        }
    }
}
