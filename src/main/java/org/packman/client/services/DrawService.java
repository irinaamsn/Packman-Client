package org.packman.client.services;

import org.packman.client.enums.MoveKeys;

public interface DrawService {
    void drawStartGame(String username);
    void drawUpdateWindow();//todo нужен ли он сереже ?????
    void drawMoveKeys(MoveKeys move);
    public void drawForceGame();
    void drawMenu();
}
