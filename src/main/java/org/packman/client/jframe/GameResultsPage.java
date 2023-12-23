package org.packman.client.jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameResultsPage extends JFrame {
    private JLabel usernameLabel;
    private JLabel pointsLabel;
    private JLabel positionLabel;
    private JList<AppUser> bestPlayersList;
    private JButton playAgainButton;
    private JButton menuButton;

    public GameResultsPage(String username, List<AppUser> bestPlayers, int currentPoints, int currentPosition) {
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
                // Действия при нажатии на кнопку "Играть снова"
                // Например, открытие новой игровой страницы
                dispose(); // Закрываем текущую страницу
                // Ваш код для начала новой игры
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действия при нажатии на кнопку "Меню"
                // Например, возвращение на главное меню
                dispose(); // Закрываем текущую страницу
                // Ваш код для перехода в главное меню
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Пример использования
            List<AppUser> bestPlayers = List.of(
                    new AppUser("Player1", 100),
                    new AppUser("Player2", 90),
                    new AppUser("Player3", 80)
            );

            new GameResultsPage("CurrentPlayer", bestPlayers, 120, 1);
        });
    }
}

class AppUser {
    private String username;
    private int countPoints;

    public AppUser(String username, int countPoints) {
        this.username = username;
        this.countPoints = countPoints;
    }

    @Override
    public String toString() {
        return username + ": " + countPoints;
    }
}

