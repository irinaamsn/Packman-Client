package org.packman.client.handler;

import lombok.RequiredArgsConstructor;
import org.packman.client.services.DrawService;

@RequiredArgsConstructor
public class ButtonClickHandler {
    private final DrawService drawService;

    //
    void clickStart() {
        drawService.drawStartGame(" ");
    }

    void clickMenu() {
        drawService.drawMenu();
    }

    void clickForce() {
        drawService.drawForceGame();
    }
}
