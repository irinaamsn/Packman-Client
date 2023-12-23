package org.packman.client.handler;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.packman.client.services.DrawService;

@RequiredArgsConstructor
public class ButtonClickHandler {
    private final DrawService drawService;

    public void clickStart(String username) {
        drawService.drawStartGame(username);
    }

    public void clickMenu() {
        drawService.drawMenu();
    }

    public void clickForce() {
        drawService.drawForceGame();
    }
}
