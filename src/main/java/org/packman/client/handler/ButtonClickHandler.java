package org.packman.client.handler;

import lombok.RequiredArgsConstructor;
import org.packman.client.services.DrawService;

/*
 * начать игру
 * ввести ник
 * выход
 * ?
 */
@RequiredArgsConstructor
public class ButtonClickHandler {
    private final DrawService drawService;
    //
    void clickStart() {
        drawService.drawStartGame(" ");
    }

    void clickMenu() {
        drawService.drawMenu();
     //   ClientSocket.changePlayer();
    }
    void clickForce() {
        drawService.drawForceGame();
    }
}
