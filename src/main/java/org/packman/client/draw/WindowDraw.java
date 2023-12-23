package org.packman.client.draw;

import lombok.RequiredArgsConstructor;
import org.packman.client.jframe.GameMenu;
import org.packman.client.jframe.GamePage;
import org.packman.client.jframe.GameResultsPage;
import org.packman.client.models.AppUser;
import org.packman.client.services.impl.DrawServiceImpl;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class WindowDraw {
    private final GameMenu gameMenu;
    private final GamePage gamePage;
    private final GameResultsPage gameResultsPage;
    public void drawMenu(List<AppUser> bestPlayers, Consumer<String> funk) {
        gameMenu.draw(bestPlayers, funk);
    }

    public void drawFinish(String username, List<AppUser> bestPlayers, int currentPoints, int currentPosition) {
        gameResultsPage.draw(username, bestPlayers, currentPoints, currentPosition);
    }

    public void updateGame(List<int[]> map, int timeLeft, int currentPoints, DrawServiceImpl drawService){
        gamePage.draw(map, timeLeft, currentPoints, drawService);
    }
    public void updateLifeTime(int timeLife){
        gamePage.updateTimeLabel(timeLife);
    }
}
