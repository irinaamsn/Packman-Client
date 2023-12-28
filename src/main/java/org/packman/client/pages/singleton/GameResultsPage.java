package org.packman.client.pages.singleton;

import org.packman.client.models.AppUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

//@RequiredArgsConstructor
public class GameResultsPage extends JFrame {
    private static GameResultsPage instance;
    private GameResultsPage(){
    }
    public static GameResultsPage getInstance(){
        if (instance == null) {
            instance = new GameResultsPage();
        }
        return instance;
    }
    private JLabel usernameLabel;
    private JLabel pointsLabel;
    private JLabel positionLabel;
    private JList<AppUser> bestPlayersList;
    private JButton playAgainButton;
    private JButton menuButton;
    public void draw(String username, List<AppUser> bestPlayers, int currentPoints, int currentPosition,
                     Consumer<String> onClickStartGame, Runnable onClickStartMenu) {
        setTitle("Результаты игры");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);

        // Левая панель с результатами текущего игрока и кнопками
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        usernameLabel = new JLabel("Имя: " + username);
        pointsLabel = new JLabel("Очки: " + currentPoints);
        positionLabel = new JLabel("Позиция: " + currentPosition);
        playAgainButton = new JButton("Играть снова");
        menuButton = new JButton("Меню");

        leftPanel.add(usernameLabel);
        leftPanel.add(pointsLabel);
        leftPanel.add(positionLabel);
        leftPanel.add(playAgainButton);
        leftPanel.add(menuButton);

        // Правая панель со списком лучших игроков
        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel bestPlayersLabel = new JLabel("Лучшие игроки:");
        bestPlayersList = new JList<>(bestPlayers.toArray(new AppUser[0]));
        JScrollPane scrollPane = new JScrollPane(bestPlayersList);
        rightPanel.add(bestPlayersLabel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Размещение панелей на форме
        setLayout(new GridLayout(1, 2));
        add(leftPanel);
        add(rightPanel);

        // Обработчики событий для кнопок
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickStartGame.accept(username);
                dispose();
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickStartMenu.run();
                dispose();
            }
        });

        // Обновление отображения результатов текущего игрока и списка лучших игроков
        updateResults(username, currentPoints, currentPosition);
        updateBestPlayersList(bestPlayers);
        setVisible(true);
    }

    public void updateResults(String username, int currentPoints, int currentPosition) {
        usernameLabel.setText("Имя: " + username);
        pointsLabel.setText("Очки: " + currentPoints);
        positionLabel.setText("Позиция: " + currentPosition);
    }

    public void updateBestPlayersList(List<AppUser> bestPlayers) {
        bestPlayersList.setListData(bestPlayers.toArray(new AppUser[0]));
    }
}


