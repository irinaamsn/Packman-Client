package org.packman.client.jframe;

import lombok.RequiredArgsConstructor;
import org.packman.client.services.impl.DrawServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
public class GamePage extends JFrame {
    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JPanel mapPanel;

    public void draw(List<int[]> map, int timeLife, int currentPoints, DrawServiceImpl drawService) {
        setTitle("Игровая страница");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
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

        addKeyListener(drawService);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

