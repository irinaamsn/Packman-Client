package org.packman.client.handler;

import lombok.RequiredArgsConstructor;
import org.packman.client.enums.MoveKeys;
import org.packman.client.services.DrawService;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public class KeyEventHandler extends KeyAdapter {
    private final DrawService drawService;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> drawService.drawMoveKeys(MoveKeys.MOVE_UP);
            case KeyEvent.VK_DOWN -> drawService.drawMoveKeys(MoveKeys.MOVE_DOWN);
            case KeyEvent.VK_LEFT -> drawService.drawMoveKeys(MoveKeys.MOVE_LEFT);
            case KeyEvent.VK_RIGHT -> drawService.drawMoveKeys(MoveKeys.MOVE_RIGHT);
        }
    }
}
