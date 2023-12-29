package org.packman.client.pages.singleton;

import org.packman.client.models.AppUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class GameMenu extends JFrame {
    JButton startButton = new JButton("ИГРАТЬ");
    JButton exitButton = new JButton("ВЫЙТИ");
    JTextField usernameField = new JTextField("Чочовец", 15);
    private static GameMenu instance;

    private GameMenu() {}

    public static GameMenu getInstance() {
        if (instance == null) {
            instance = new GameMenu();
        }
        return instance;
    }

    public void draw(List<AppUser> bestPlayers, Consumer<String> onClickStart, Runnable onClickExit) {
        getContentPane().removeAll();
        setTitle("МЕНЮ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 666);
        setLocationRelativeTo(null);
        JLabel title = new JLabel("ЧоЧ");
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Левая панель
        JPanel leftPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 55, 10, 90); // Устанавливаем минимальные отступы


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.add(title);
        leftPanel.add(titlePanel, gbc);
        // Панель для ввода имени
        gbc.gridy++;
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        inputPanel.add(usernameField);
        leftPanel.add(inputPanel, gbc);

        title.setFont(new Font(title.getName(), Font.BOLD, 40));

        // Панель для кнопки "Старт"
        gbc.gridy++;
        JPanel startButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        startButtonPanel.add(startButton);
        leftPanel.add(startButtonPanel, gbc);

        // Панель для кнопки "Выйти"
        gbc.gridy++;
        JPanel exitButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        exitButtonPanel.add(exitButton);
        leftPanel.add(exitButtonPanel, gbc);

        Dimension textFieldSize = new Dimension(200, 30);
        usernameField.setPreferredSize(textFieldSize);

        Dimension buttonSize = new Dimension(150, 50);
        startButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        // Установка обработчика событий для кнопок "Старт" и "Выйти"
        startButton.addActionListener(e -> {
            String username = usernameField.getText();
            onClickStart.accept(username);
            dispose();
        });

        exitButton.addActionListener(e -> {
            onClickExit.run();
            dispose();
        });

        mainPanel.add(leftPanel, BorderLayout.WEST);
        StringBuilder playersText = new StringBuilder("<html>");
        playersText.append("<b>Список лучших игроков:</b>").append("<br>").append("<br>");
        for (int i = 0; i < bestPlayers.size(); i++) {
            AppUser player = bestPlayers.get(i);
            playersText.append(i + 1).append(". ")
                    .append(player.getUsername()).append(": ")
                    .append(player.getCountPoints()).append("<br>").append("<br>");
        }
        playersText.append("</html>");


        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel bestPlayersLabel = new JLabel(playersText.toString());
        rightPanel.add(bestPlayersLabel, BorderLayout.CENTER);

// Размещение панелей на форме с центрированием по горизонтали
        setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 0.5;
        add(rightPanel, gbc2);
        gbc2.gridx = 1;
        gbc2.insets = new Insets(0, 10, 0, 0); // Добавляем отступ между панелями
        add(rightPanel, gbc2);


        // Добавление правой панели в EAST
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Добавление основной панели к JFrame
        add(mainPanel);

        // Обновление топа лучших игроков
//        updateLeaderboard(bestPlayers);

        setVisible(true);
    }

}
