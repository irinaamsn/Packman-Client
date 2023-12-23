package org.packman.client.services.impl;

import lombok.RequiredArgsConstructor;
import org.packman.client.draw.WindowDraw;
import org.packman.client.enums.Command;
import org.packman.client.enums.MoveKeys;
import org.packman.client.enums.answers.FinishAnswer;
import org.packman.client.enums.answers.MoveAnswer;
import org.packman.client.enums.answers.UpdateMapAnswer;
import org.packman.client.models.AppUser;
import org.packman.client.services.DrawService;
import org.packman.client.socket.ClientCommand;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.packman.client.utils.ParseUtil.*;
import static org.packman.client.utils.PropertiesUtil.getPeriod;
import static org.packman.client.utils.PropertiesUtil.getTimeGame;

@RequiredArgsConstructor
public class DrawServiceImpl implements DrawService {
    private static String USERNAME;
    private final int PERIOD_GAME = getPeriod();//1
    private final int TIME_GAME = getTimeGame();//100

    private final WindowDraw draw;

    private final ClientCommand clientCommand;
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void drawStartGame(String username) {
        USERNAME = username;
        String response = clientCommand.sendCommand(Command.START.name(), username);
        String[] parseResponse = parseStrToArray(response);
        List<int[]> map = toMap(parseResponse[1]);
        int timeLeft = Integer.valueOf(parseResponse[2]);
        draw.updateGame(map, timeLeft, 0);
        long startTime = System.currentTimeMillis();
        scheduler.scheduleAtFixedRate(() -> {
            if (System.currentTimeMillis() - startTime < TIME_GAME) {
                drawUpdateWindow();
            } else {
                scheduler.shutdown();
            }
        }, 0, PERIOD_GAME, TimeUnit.SECONDS);
        /*scheduler.schedule(() ->
                 scheduler.scheduleAtFixedRate(this::drawUpdateWindow, 0, PERIOD_GAME, TimeUnit.SECONDS)
                .cancel(false), TIME_GAME, TimeUnit.SECONDS
        );*/
       /* scheduler.scheduleAtFixedRate(this::drawUpdateWindow, 0, PERIOD_GAME, TimeUnit.SECONDS);
        scheduler.schedule(() -> scheduler.shutdown(), TIME_GAME, TimeUnit.SECONDS);*/
    }

    @Override
    public void drawUpdateWindow() {
        String response = clientCommand.sendCommand(Command.UPDATE_MAP.name());
        String[] parseResponse = parseStrToArray(response);
        if (parseResponse[0].equals(UpdateMapAnswer.NOT_CHANGED.name()))
            draw.updateLifeTime(Integer.valueOf(parseResponse[1]));//time
        else if (parseResponse[0].equals(UpdateMapAnswer.MAP.name())) {
            List<int[]> map = toMap(parseResponse[1]);
            int timeLeft = Integer.valueOf(parseResponse[2]);
            int currentPoints = Integer.valueOf(parseResponse[3]);
            draw.updateGame(map, timeLeft, currentPoints);
        } else if (parseResponse[0].equals(UpdateMapAnswer.FINISH_GAME.name()))
            drawFinishGame(Integer.valueOf(parseResponse[1]), Integer.valueOf(parseResponse[2]));
        else drawMenu();
        ;
    }

    @Override
    public void drawMoveKeys(MoveKeys move) {
        String response = clientCommand.sendCommand(move.name());
        String[] parseResponse = parseStrToArray(response);
        if (parseResponse[0].equals(MoveAnswer.NOT_CHANGED.name()))
            draw.updateLifeTime(Integer.valueOf(parseResponse[1]));
        else if (parseResponse[0].equals(MoveAnswer.MAP.name())) {
            List<int[]> map = toMap(parseResponse[1]);
            int timeLeft = Integer.valueOf(parseResponse[2]);
            int currentPoints = Integer.valueOf(parseResponse[3]);
            draw.updateGame(map, timeLeft, currentPoints);
        } else if (parseResponse[0].equals(MoveAnswer.FINISH_GAME.name()))
            drawFinishGame(Integer.valueOf(parseResponse[1]), Integer.valueOf(parseResponse[2]));
        else drawMenu();
    }

    private void drawFinishGame(int currentPoints, int currentPosition) {//когда ответ финиш
        String responsePlayers = clientCommand.sendCommand(Command.GET_BEST_PLAYERS.name());
        String[] parseResponsePlayers = parseStrToArray(responsePlayers);
        List<AppUser> appUsers = toListBestPlayers(parseResponsePlayers[1]);
        draw.drawFinish(USERNAME, appUsers, currentPoints, currentPosition);
        scheduler.shutdown();
    }

    @Override
    public void drawForceGame() {
        String response = clientCommand.sendCommand(Command.FORCE_FINISH.name());
        String[] parseResponse = parseStrToArray(response);
        if (parseResponse[0].equals(FinishAnswer.ERROR_GAME_NOT_EXISTS.name())) drawMenu();
        else drawFinishGame(Integer.valueOf(parseResponse[1]), Integer.valueOf(parseResponse[2]));
    }

    @Override
    public void drawMenu() {
        String response = clientCommand.sendCommand(Command.GET_BEST_PLAYERS.name());
        String[] parseResponse = parseStrToArray(response);
        List<AppUser> appUsers = toListBestPlayers(parseResponse[1]);
        draw.drawMenu(appUsers);
    }
}
