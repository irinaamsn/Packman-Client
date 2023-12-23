package org.packman.client.jframe;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class GamePage extends JFrame {
    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JPanel mapPanel;

    public GamePage(List<int[]> map, int timeLife, int currentPoints) {
        setTitle("Игровая страница");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // Верхняя панель с временем и очками
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timeLabel = new JLabel("Время: " + timeLife);
        pointsLabel = new JLabel("Очки: " + currentPoints);
        topPanel.add(timeLabel);
        topPanel.add(pointsLabel);

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
    }

    public void updateTimeLabel(int timeLife) {
        timeLabel.setText("Время: " + timeLife);
    }

    public void updatePointsLabel(int currentPoints) {
        pointsLabel.setText("Очки: " + currentPoints);
    }

    public void updateMap(List<int[]> map) {
        ((MapPanel) mapPanel).updateMap(map);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Пример использования
            List<int[]> map = List.of(
                    new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
                    );

            int timeLife = 59;
            int currentPoints = 100;

            new GamePage(map, timeLife, currentPoints);
        });
    }
}

class MapPanel extends JPanel {
    private List<int[]> map;

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
                switch (map.get(i)[j]) {
                    case 0:
                        g.setColor(Color.WHITE); // Пустое место
                        break;
                    case 1:
                        g.setColor(Color.BLUE); // Игрок
                        break;
                    case 2:
                        g.setColor(Color.BLACK); // Стена
                        break;
                    case 3:
                        g.setColor(Color.YELLOW); // Монета
                        break;
                    case 4:
                        g.setColor(Color.RED); // Монета, которая скоро исчезнет
                        break;
                    default:
                        g.setColor(Color.WHITE);
                }

                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
}

