package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyProtocolClient {
    private static final String SERVER_ADDRESS = "10.102.161.58";//todo
    private static final int PORT = 2020;
    public void connection(){
        try(Socket socket = new Socket(SERVER_ADDRESS, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            // Пример отправки команды серверу
            out.println("HELLO");
            System.out.println("Received from server: " + in.readLine());

            out.println("GOODBYE");
            System.out.println("Received from server: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
