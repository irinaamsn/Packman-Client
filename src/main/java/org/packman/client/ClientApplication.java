package org.packman.client;

import org.packman.client.services.DrawService;
import org.packman.client.services.impl.DrawServiceImpl;

public class ClientApplication {
    private static final DrawService drawService = new DrawServiceImpl();
    public static void main(String[] args) {
        drawService.tryConnection();
        System.out.println();
    }
}