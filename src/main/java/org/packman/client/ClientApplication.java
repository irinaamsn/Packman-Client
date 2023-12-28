package org.packman.client;

import org.packman.client.services.DrawService;
import org.packman.client.services.impl.DrawServiceImpl;

import static org.packman.client.socket.ClientSocket.connection;

public class ClientApplication {
    private static final DrawService drawService = new DrawServiceImpl();
    public static void main(String[] args) {
//        drawService.tryConnection();
        connection();
    }
}