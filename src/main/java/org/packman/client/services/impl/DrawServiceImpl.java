package org.packman.client.services.impl;

import org.packman.client.draw.WindowDraw;
import org.packman.client.enums.Command;
import org.packman.client.enums.MoveKeys;
import org.packman.client.enums.answers.FinishAnswer;
import org.packman.client.enums.answers.MoveAnswer;
import org.packman.client.enums.answers.UpdateMapAnswer;
import org.packman.client.models.AppUser;
import org.packman.client.services.DrawService;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.packman.client.socket.ClientSocket.sendCommand;
import static org.packman.client.utils.ParseUtil.*;
import static org.packman.client.utils.PropertiesUtil.getPeriod;
import static org.packman.client.utils.PropertiesUtil.getTimeGame;

public class DrawServiceImpl extends KeyAdapter implements DrawService {
    private static String USERNAME;
    private final int PERIOD_GAME = getPeriod();
    private final int TIME_GAME = getTimeGame();

    private WindowDraw draw;

    public DrawServiceImpl() {
        draw = new WindowDraw();
    }

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void drawStartGame(String username) {
        USERNAME = username;
        String response = sendCommand(Command.START.name(), username);
        String[] parseResponse = parseStrToArray(response);
        List<int[]> map = toMap(parseResponse[1]);
        int timeLeft = Integer.parseInt(parseResponse[2]);
        draw.updateGame(map, timeLeft, 0, this, this::onForceFinishGame);
        scheduler.scheduleAtFixedRate(this::drawUpdateWindow, 0, PERIOD_GAME, TimeUnit.SECONDS);//todo scheduler
    }

    @Override
    public void drawUpdateWindow() {
        String response = sendCommand(Command.UPDATE_MAP.name());
        String[] parseResponse = parseStrToArray(response);
        if (parseResponse[0].equals(UpdateMapAnswer.NOT_CHANGED.name()))
            draw.updateLifeTime(Integer.parseInt(parseResponse[1]));//time
        else if (parseResponse[0].equals(UpdateMapAnswer.MAP.name())) {
            List<int[]> map = toMap(parseResponse[1]);
            int timeLeft = Integer.parseInt(parseResponse[2]);
            int currentPoints = Integer.parseInt(parseResponse[3]);
            draw.updateGame(map, timeLeft, currentPoints, this, this::onForceFinishGame);
        } else if (parseResponse[0].equals(UpdateMapAnswer.FINISH_GAME.name()))
            drawFinishPage(Integer.parseInt(parseResponse[1]), Integer.parseInt(parseResponse[2]));
        else drawMenu();
    }

    @Override
    public void drawMoveKeys(MoveKeys move) {
        String response = sendCommand(move.name());
        String[] parseResponse = parseStrToArray(response);
        if (parseResponse[0].equals(MoveAnswer.NOT_CHANGED.name()))
            draw.updateLifeTime(Integer.parseInt(parseResponse[1]));
        else if (parseResponse[0].equals(MoveAnswer.MAP.name())) {
            List<int[]> map = toMap(parseResponse[1]);
            int timeLeft = Integer.parseInt(parseResponse[2]);
            int currentPoints = Integer.parseInt(parseResponse[3]);
            draw.updateGame(map, timeLeft, currentPoints, this, this::onForceFinishGame);
        } else if (parseResponse[0].equals(MoveAnswer.FINISH_GAME.name()))
            drawFinishPage(Integer.parseInt(parseResponse[1]), Integer.parseInt(parseResponse[2]));
        else drawMenu();
    }

    private void drawFinishPage(int currentPoints, int currentPosition) {
        String responsePlayers = sendCommand(Command.GET_BEST_PLAYERS.name());
        String[] parseResponsePlayers = parseStrToArray(responsePlayers);
        List<AppUser> appUsers = toListBestPlayers(parseResponsePlayers[1]);
        draw.drawFinish(USERNAME, appUsers, currentPoints, currentPosition, this::drawStartGame, this::drawMenu);
        scheduler.shutdown();
    }

    @Override
    public void onForceFinishGame() {
        String response = sendCommand(Command.FORCE_FINISH.name());
        String[] parseResponse = parseStrToArray(response);
        if (parseResponse[0].equals(FinishAnswer.ERROR_GAME_NOT_EXISTS.name())) drawMenu();
        else drawFinishPage(Integer.parseInt(parseResponse[1]), Integer.parseInt(parseResponse[2]));
    }

    @Override
    public void drawMenu() {
        String response = sendCommand(Command.GET_BEST_PLAYERS.name());
        String[] parseResponse = parseStrToArray(response);
        List<AppUser> appUsers = toListBestPlayers(parseResponse[1]);
        draw.drawMenu(appUsers, this::drawStartGame);
    }

    @Override
    public void closeConnection() {
        String response = sendCommand(Command.QUIT.name());
        //todo response check
    }

    static Long lastKeyPressed = 0L;
    static Long MIN_TIME_KEY_PRESSED_PRESSED_SUPPORTED = 100L;

    @Override
    public void keyPressed(KeyEvent e) {
        Long currentTime = System.currentTimeMillis();
        if (currentTime - lastKeyPressed < MIN_TIME_KEY_PRESSED_PRESSED_SUPPORTED) return;
        lastKeyPressed = currentTime;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                drawMoveKeys(MoveKeys.MOVE_UP);
                System.out.println("Up key pressed");
            }
            case KeyEvent.VK_DOWN -> {
                drawMoveKeys(MoveKeys.MOVE_DOWN);
                System.out.println("Down key pressed");
            }
            case KeyEvent.VK_LEFT -> {
                drawMoveKeys(MoveKeys.MOVE_LEFT);
                System.out.println("Left key pressed");
            }
            case KeyEvent.VK_RIGHT -> {
                drawMoveKeys(MoveKeys.MOVE_RIGHT);
                System.out.println("Right key pressed");
            }
        }
    }
}
