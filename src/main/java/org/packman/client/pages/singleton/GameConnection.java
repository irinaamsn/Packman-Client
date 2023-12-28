package org.packman.client.pages.singleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.packman.client.socket.ClientSocket.connection;

public class GameConnection extends JFrame {
    private static GameConnection instance;

    private GameConnection() {
    }

    public static GameConnection getInstance() {
        if (instance == null) {
            instance = new GameConnection();
        }
        return instance;
    }

    public void draw() {
        // Установка заголовка и размеров окна
        setTitle("Главное окно");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание панели с макетом BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Создание приветственной метки
        JLabel welcomeLabel = new JLabel("Добро пожаловать!");

        // Создание кнопки "Подключиться к серверу"
        JButton connectButton = new JButton("Подключиться к серверу");
        connectButton.addActionListener(e -> {
            try {
//                setVisible(false);
                dispose();
                connection();
            } catch (Exception ex) {
                throw new RuntimeException();
            }
        });

        // Добавление компонентов на панель
        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(connectButton, BorderLayout.SOUTH);

        // Добавление панели на окно
        add(panel);
        setVisible(true);
    }

    static class ExceptionFrame extends JFrame {

        public ExceptionFrame() {
            // Установка заголовка и размеров окна
            setTitle("Окно подключения");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Создание панели с макетом BorderLayout
            JPanel panel = new JPanel(new BorderLayout());

            // Создание приветственной метки для окна подключения
            JLabel connectedLabel = new JLabel("Подключено к серверу!");

            // Создание кнопки "Ок"
            JButton okButton = new JButton("Ок");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    GameMenu gameMenu = GameMenu.getInstance();
//                    gameMenu.setVisible(true);
//                    setVisible(false);
//                    dispose();
//                    connection();
                }
            });

            // Добавление компонентов на панель
            panel.add(connectedLabel, BorderLayout.CENTER);
            panel.add(okButton, BorderLayout.SOUTH);

            // Добавление панели на окно
            add(panel);
            setVisible(true);
        }
    }
}
