package org.packman.client.draw;

import lombok.RequiredArgsConstructor;
import org.packman.client.pages.GameMenu;
import org.packman.client.pages.GamePage;
import org.packman.client.pages.GameResultsPage;
import org.packman.client.models.AppUser;
import org.packman.client.services.impl.DrawServiceImpl;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class WindowDraw {
    private final GameMenu gameMenu;
    private final GamePage gamePage;
    private final GameResultsPage gameResultsPage;

    public void drawMenu(
            List<AppUser> bestPlayers,
            Consumer<String> onClickStart
    ) {
        gameMenu.draw(bestPlayers, onClickStart);
    }

    public void drawFinish(
            String username,
            List<AppUser> bestPlayers,
            int currentPoints,
            int currentPosition,
            Consumer<String> onClickStartGame,
            Runnable onClickStartMenu
    ) {
        gameResultsPage.draw(username, bestPlayers, currentPoints, currentPosition, onClickStartGame, onClickStartMenu);
    }

    public void updateGame(
            List<int[]> map, int timeLeft,
            int currentPoints,
            DrawServiceImpl drawService,
            Runnable onClickForceFinishGame
    ) {
        gamePage.draw(map, timeLeft, currentPoints, drawService, onClickForceFinishGame);
    }

    public void updateLifeTime(int timeLife) {
        gamePage.updateTimeLabel(timeLife);
    }
}
