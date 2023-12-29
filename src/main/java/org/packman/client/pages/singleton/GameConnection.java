package org.packman.client.pages.singleton;

import org.packman.client.exceptions.SocketException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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

    private JLabel welcomeLabel;
    private JButton connectButton;
    private JLabel loaderLabel;
    private JLabel exceptionMessage;

    public void draw() {
        // Установка заголовка и размеров окна
        setTitle("Подключение");
        setSize(800, 666);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание панели с макетом GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Создание приветственной метки
        welcomeLabel = new JLabel("Добро пожаловать!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.CENTER);

        // Загрузка GIF-анимации для крутилки
        URL loaderUrl = getClass().getResource("/gif/menu/loader.gif");
        if (loaderUrl != null) {
            int loaderWidth = 200;
            int loaderHeight = 200;

            ImageIcon loaderIcon = new ImageIcon(loaderUrl);
            Image loaderImage = loaderIcon.getImage().getScaledInstance(loaderWidth, loaderHeight, Image.SCALE_DEFAULT);

            // Создание метки для отображения крутилки
            loaderLabel = new JLabel(new ImageIcon(loaderImage));
            loaderLabel.setVisible(false);
            loaderLabel.setSize(new Dimension(loaderWidth, loaderHeight));
        }

        // Создание кнопки "Подключиться к серверу"
        connectButton = new JButton("Подключиться к серверу");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectButton.setEnabled(false); // Запрещаем повторное нажатие кнопки
                loaderLabel.setVisible(true); // Показываем крутилку
                welcomeLabel.setText("Идет подключение...");
                exceptionMessage.setVisible(false);

                new Thread(() -> {
                    try {
                        connection();
                        dispose();
                    } catch (Exception ex) {
                        exceptionMessage.setText("Ошибка подключения!");
                        exceptionMessage.setVisible(true);
                        loaderLabel.setVisible(false); // Скрываем крутилку
                        connectButton.setEnabled(true); // Разрешаем повторное нажатие кнопки
                        welcomeLabel.setText("Добро пожаловать!");
                    }
                }).start();
            }
        });

        // Создание метки для отображения ошибки подключения
        exceptionMessage = new JLabel();
        exceptionMessage.setForeground(Color.RED);

        // Настройка параметров GridBagConstraints для центрирования компонентов
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(20, 0, 0, 0); // Вертикальный отступ

        // Добавление компонентов на панель
        panel.add(welcomeLabel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0); // Вертикальный отступ
        panel.add(connectButton, gbc);
        gbc.gridy = 2;
        panel.add(loaderLabel, gbc);
        gbc.gridy = 3;
        panel.add(exceptionMessage, gbc);

        // Добавление панели на окно
        add(panel);

        // Размещение окна посередине экрана
        setLocationRelativeTo(null);

        setVisible(true);
    }
}


