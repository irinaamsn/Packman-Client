package org.packman.client.jframe;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.packman.client.handler.ButtonClickHandler;
import org.packman.client.models.AppUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
public class GameMenu extends JFrame {
    private final JButton startButton;
    private final JTextField usernameField;
    private final JTextArea leaderboardArea;

    //private final ButtonClickHandler handler;

    public GameMenu(List<AppUser> bestPlayers) {
        setTitle("МЕНЮ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // Панель для ввода имени и кнопки "Старт"
        JPanel inputPanel = new JPanel(new FlowLayout());
        startButton = new JButton("СТАРТ");
        usernameField = new JTextField("Введите имя", 15);
        inputPanel.add(usernameField);
        inputPanel.add(startButton);

        // Панель для отображения топа лучших игроков
        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardArea);
        leaderboardPanel.add(new JLabel("Топ игроков:"), BorderLayout.NORTH);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);

        // Добавление панелей к основной панели
        mainPanel.add(inputPanel);
        mainPanel.add(leaderboardPanel);

        // Добавление основной панели к JFrame
        add(mainPanel);

        // Установка обработчика событий для кнопки "Старт"
        startButton.addActionListener(e -> {
            String username = usernameField.getText();
            //handler.clickStart(username);
        });

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Пример использования
            List<AppUser> bestPlayers = List.of(
                    new AppUser("Player1", 100),
                    new AppUser("Player2", 90),
                    new AppUser("Player3", 80)
            );

            new GameMenu(bestPlayers);
        });
    }
}

