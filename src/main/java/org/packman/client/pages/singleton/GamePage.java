package org.packman.client.pages.singleton;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;
import org.packman.client.services.impl.DrawServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.util.List;

public class GamePage extends JFrame {
    private static GamePage instance;
    private GamePage(){
    }
    public static GamePage getInstance(){
        if (instance == null) {
            instance = new GamePage();
        }
        return instance;
    }
    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JPanel mapPanel;

    private int previousPoints = 0;
    private boolean isPlayBackgroundMusic = false;
    private boolean isPageActive = false;

    private String filePathCoin = "src/main/resources/raw/coin.mp3";
    private String filePathBackgroundMusic = "src/main/resources/raw/background_music.mp3";

    private FileInputStream fileInputStream;
    private AdvancedPlayer playerBackgroundMusic;

    public void draw(List<int[]> map, int timeLife, int currentPoints, DrawServiceImpl drawService,
                     Runnable onClickForceFinishGame) {
        if (!isPlayBackgroundMusic) {
            new Thread(() -> {
                try {
                    playBackgroundMusic();
                } catch (Exception ignored) {
                }
            }).start();
            isPlayBackgroundMusic = true;
        }
        isPageActive = true;
        getContentPane().removeAll();
        setTitle("Пакмэн");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 666);
        setLocationRelativeTo(null);

        // Верхняя панель с временем, очками и кнопкой
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        timeLabel = new JLabel("Время: " + timeLife);
        pointsLabel = new JLabel("Очки: " + currentPoints);

        JButton finishButton = new JButton("Завершить игру");
        finishButton.addActionListener((ActionEvent e) -> {
            onClickForceFinishGame.run();
            finish();
        });

        topPanel.add(timeLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topPanel.add(pointsLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topPanel.add(finishButton);

        // Нижняя панель с отображением карты
        mapPanel = new MapPanel(map);

        // Размещение панелей на форме
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(mapPanel, BorderLayout.CENTER);

        // Обновление отображения времени и очков
        updateTimeLabel(timeLife);
        updatePointsLabel(currentPoints);

        setVisible(true);

        addKeyListener(drawService);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void finish() {
        if (isPageActive) {
            stopBackgroundMusic();
            isPageActive = false;
            dispose();
        }
    }

    public void updateTimeLabel(int timeLife) {
        timeLabel.setText("Время: " + timeLife);
    }

    public void updatePointsLabel(int currentPoints) {
        if (currentPoints > previousPoints) {
            new Thread(() -> {
                try {
                    playMusic(filePathCoin);
                } catch (Exception ignored) {
                }
            }).start();
            previousPoints = currentPoints;
        }
        pointsLabel.setText("Очки: " + currentPoints);
    }

    public void updateMap(List<int[]> map) {
        ((MapPanel) mapPanel).updateMap(map);
    }

    public static void playMusic(String path) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(path);
        AdvancedPlayer player = new AdvancedPlayer(fileInputStream);
        player.setPlayBackListener(new PlaybackListener() {
        });
        player.play();
    }

    public void playBackgroundMusic() throws Exception {
        fileInputStream = new FileInputStream(filePathBackgroundMusic);
        playerBackgroundMusic = new AdvancedPlayer(fileInputStream);
        playerBackgroundMusic.setPlayBackListener(new PlaybackListener() {
        });
        playerBackgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        if (isPlayBackgroundMusic) {
            playerBackgroundMusic.stop();
            isPlayBackgroundMusic = false;
        }
    }
}

class MapPanel extends JPanel {
    private List<int[]> map;

    private static final Image wallImage = new ImageIcon("src/main/resources/img/wall.jpeg").getImage();
    private static final Image emptyImage = new ImageIcon("src/main/resources/img/empty.jpeg").getImage();
    private static final Image playerImage = new ImageIcon("src/main/resources/img/packman.jpeg").getImage();
    private static final Image weakCoinImage = new ImageIcon("src/main/resources/img/coin.png").getImage();
    private static final Image meddleCoinImage = new ImageIcon("src/main/resources/img/coin.png").getImage();
    private static final Image strongCoinImage = new ImageIcon("src/main/resources/img/coin.png").getImage();


    public MapPanel(List<int[]> map) {
        this.map = map;
    }

    public void updateMap(List<int[]> map) {
        this.map = map;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = 40;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length; j++) {
                Image image = null;
                switch (map.get(i)[j]) {
                    case 0:
                        image = emptyImage;
                        break;
                    case 1:
                        // Игрок
                        image = playerImage;
                        break;
                    case 2:
                        // стена
                        image = wallImage;
                        break;
                    case 3:
                        // Монета слабая
                        image = weakCoinImage;
                        break;
                    case 4:
                        // Монета слабая, скоро исчезнет
                        image = weakCoinImage;
                        break;
                    case 5:
                        // Монета средняя
                        image = meddleCoinImage;
                        break;
                    case 6:
                        // Монета средняя, скоро исчезнет
                        image = meddleCoinImage;
                        break;
                    case 7:
                        // Монета сильная
                        image = strongCoinImage;
                        break;
                    case 8:
                        // Монета сильная, скоро исчезнет
                        image = strongCoinImage;
                        break;
                    default:
                        // Другие случаи
                }

                if (image != null) {
                    g.drawImage(image, j * cellSize, i * cellSize, cellSize, cellSize, this);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
    }

}


