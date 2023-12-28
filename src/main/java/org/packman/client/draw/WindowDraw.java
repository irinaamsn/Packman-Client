package org.packman.client.draw;

import org.packman.client.models.AppUser;
import org.packman.client.pages.GameMenu;
import org.packman.client.pages.GamePage;
import org.packman.client.pages.GameResultsPage;
import org.packman.client.services.impl.DrawServiceImpl;

import java.util.List;
import java.util.function.Consumer;

public class WindowDraw {
    private static final GameMenu gameMenu = GameMenu.getInstance();
    private static final GamePage gamePage = GamePage.getInstance();
    private static final GameResultsPage gameResultsPage=GameResultsPage.getInstance();

    public static void drawMenu(
            List<AppUser> bestPlayers,
            Consumer<String> onClickStart
    ) {
        gameMenu.draw(bestPlayers, onClickStart);
    }

    public static void drawFinish(
            String username,
            List<AppUser> bestPlayers,
            int currentPoints,
            int currentPosition,
            Consumer<String> onClickStartGame,
            Runnable onClickStartMenu
    ) {
        gameMenu.close();
        gameResultsPage.draw(username, bestPlayers, currentPoints, currentPosition, onClickStartGame, onClickStartMenu);
    }

    public static void updateGame(
            List<int[]> map, int timeLeft,
            int currentPoints,
            DrawServiceImpl drawService,
            Runnable onClickForceFinishGame
    ) {
        gamePage.draw(map, timeLeft, currentPoints, drawService, onClickForceFinishGame);
    }

    public static void updateLifeTime(int timeLife) {
        gamePage.updateTimeLabel(timeLife);
    }
}
