package org.packman.client.pages.singleton;

import org.packman.client.models.AppUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class GameMenu extends JFrame {
    JButton startButton = new JButton("СТАРТ");
    JButton exitButton = new JButton("ВЫЙТИ");
    JTextField usernameField = new JTextField("Введите ник", 15);
    private static GameMenu instance;

    private GameMenu() {

    }

    public static GameMenu getInstance() {
        if (instance == null) {
            instance = new GameMenu();
        }
        return instance;
    }

    private JTextArea leaderboardArea;

    public void draw(List<AppUser> bestPlayers, Consumer<String> onClickStart, Runnable onClickExit) {
        setTitle("МЕНЮ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // Панель для ввода имени и кнопки "Старт"
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(usernameField);
        inputPanel.add(startButton);
        inputPanel.add(exitButton);
        // Панель для отображения топа лучших игроков
        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardArea);
        leaderboardPanel.add(new JLabel("Топ игроков:"), BorderLayout.NORTH);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);


        // Установка обработчика событий для кнопки "Старт"
        startButton.addActionListener(e -> {
            String username = usernameField.getText();
            onClickStart.accept(username);
//            setVisible(false);
            dispose();
        });

        exitButton.addActionListener(e -> {
            onClickExit.run();
//            setVisible(false);
            dispose();
        });

        mainPanel.add(inputPanel);
        mainPanel.add(leaderboardPanel);

        // Добавление основной панели к JFrame
        add(mainPanel);
        // Обновление топа лучших игроков
        updateLeaderboard(bestPlayers);
        setVisible(true);
    }

    public void updateLeaderboard(List<AppUser> bestPlayers) {
        StringBuilder leaderboardText = new StringBuilder();
        for (AppUser player : bestPlayers) {
            leaderboardText.append(player.getUsername()).append(": ").append(player.getCountPoints()).append("\n");
        }
        leaderboardArea.setText(leaderboardText.toString());
    }

}

