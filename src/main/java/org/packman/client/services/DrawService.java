package org.packman.client.services;

import org.packman.client.enums.MoveKeys;

import java.awt.event.KeyAdapter;

public interface DrawService {
    void drawStartGame(String username);
    void drawUpdateWindow();
    void drawMoveKeys(MoveKeys move);
    void drawForceGame();
    void drawMenu();
}
