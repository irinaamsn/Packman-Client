package org.packman.client.socket;

import lombok.RequiredArgsConstructor;
import org.packman.client.draw.WindowDraw;
import org.packman.client.pages.GameMenu;
import org.packman.client.pages.GamePage;
import org.packman.client.pages.GameResultsPage;
import org.packman.client.services.DrawService;
import org.packman.client.services.impl.DrawServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@RequiredArgsConstructor
public class ClientSocket{
    private static int PORT = 2020;
    private static String SERVER_ADDRESS ="10.102.161.58";
    private static DrawService drawService = new DrawServiceImpl(
            new WindowDraw(
                    new GameMenu(),
                    new GamePage(),
                    new GameResultsPage())
    );
   /* static {
        try {
            PORT = properties.getPort();
            SERVER_ADDRESS = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }*/

    private static PrintWriter out;
    private static BufferedReader in;

    public static void connection() {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            drawService.drawMenu();
            while (true) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendCommand(String command, String username) {
        out.println(command + " " + username);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();//todo
        }
    }

    public static String sendCommand(String command) {
        out.println(command);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();//todo
        }
    }
}
