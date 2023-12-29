package org.packman.client.pages.singleton;

import org.packman.client.models.AppUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class GameResultsPage extends JFrame {
    private static GameResultsPage instance;

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel pointsLabel;
    private JLabel positionLabel;
    private JButton playAgainButton;
    private JButton exitButton;
    private JLabel bestPlayersTitleLabel;
    private JLabel bestPlayersLabel;

    private GameResultsPage() {
    }

    public static GameResultsPage getInstance() {
        if (instance == null) {
            instance = new GameResultsPage();
        }
        return instance;
    }

    public void draw(String username, List<AppUser> bestPlayers, int currentPoints, int currentPosition,
                     Consumer<String> onClickStartGame, Runnable onClickExit) {
        setTitle("Пакмэн");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 666);
        setLocationRelativeTo(null);
        getContentPane().removeAll();

        StringBuilder playersText = new StringBuilder("<html>");
        for (int i = 0; i < bestPlayers.size(); i++) {
            AppUser player = bestPlayers.get(i);
            playersText.append(i + 1).append(". ")
                    .append(player.getUsername()).append(": ")
                    .append(player.getCountPoints()).append("<br>");
        }
        playersText.append("</html>");

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Результаты игры");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        usernameLabel = new JLabel("Имя: " + username);
        pointsLabel = new JLabel("Очки: " + currentPoints);
        positionLabel = new JLabel("Позиция: " + currentPosition);
        playAgainButton = new JButton("Играть снова");
        exitButton = new JButton("ВЫЙТИ");

        Dimension buttonSize = new Dimension(150, 50);
        playAgainButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        Font labelFont = usernameLabel.getFont();
        Font newLabelFont = new Font(labelFont.getName(), Font.PLAIN, 18);
        usernameLabel.setFont(newLabelFont);
        pointsLabel.setFont(newLabelFont);
        positionLabel.setFont(newLabelFont);

        playAgainButton.setFont(new Font(labelFont.getName(), Font.BOLD, 16));
        exitButton.setFont(new Font(labelFont.getName(), Font.BOLD, 16));

        // Add vertical glue to center-align components
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(usernameLabel);
        leftPanel.add(pointsLabel);
        leftPanel.add(positionLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical space
        leftPanel.add(playAgainButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical space
        leftPanel.add(exitButton);
        leftPanel.add(Box.createVerticalGlue());

        JPanel rightPanel = new JPanel(new BorderLayout());
        bestPlayersTitleLabel = new JLabel("Список лучших игроков");
        bestPlayersTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        bestPlayersTitleLabel.setFont(new Font(bestPlayersTitleLabel.getFont().getName(), Font.BOLD, 18));
        bestPlayersLabel = new JLabel(playersText.toString());

        bestPlayersLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 18));

        rightPanel.add(bestPlayersTitleLabel, BorderLayout.NORTH);
        rightPanel.add(bestPlayersLabel, BorderLayout.CENTER);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        add(leftPanel, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 20, 0, 0); // Add some space to the right
        add(rightPanel, gbc);

        playAgainButton.addActionListener(e -> {
            dispose();
            onClickStartGame.accept(username);
        });

        exitButton.addActionListener(e -> {
            onClickExit.run();
        });

        // Добавление основной панели к JFrame
        setVisible(true);
    }
}
