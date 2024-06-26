package ru.itsjava.services;

import dao.UserDao;

import dao.UserNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.itsjava.domain.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

@RequiredArgsConstructor
@Getter
public class ClientRunnable implements Runnable, Observer {
    private final Socket socket;
    private final ServerService serverService;
    private User user;
    private final UserDao userDao;


    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Client connected");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String messageFromClient;
        while (true) {
            System.out.println("Enter !autho!login:password for authorization or !register!login:password for registration:");
            String initialMessage = bufferedReader.readLine(); // Чтение строки от клиента
            if (initialMessage.startsWith("!autho!")) {
                if (authorization(initialMessage)) break; // Если авторизация успешна, выйти из цикла
            } else if (initialMessage.startsWith("!register!")) {
                if (register(initialMessage)) break; // Если регистрация успешна, выйти из цикла
            } else {
                System.out.println("Invalid input. Please enter a valid command.");
            }
        }

        // Добавление текущего клиента в список наблюдателей
        serverService.addObserver(this);

        // Чтение сообщений от клиента и их рассылка всем остальным клиентам
        while ((messageFromClient = bufferedReader.readLine()) != null) {
            System.out.println(user.getName() + ": " + messageFromClient);
            serverService.notifyObserversExceptMe(user.getName() + ": " + messageFromClient, this);
        }


    }

    @SneakyThrows
    private boolean authorization(String authorizationMessage) {
        String[] parts = authorizationMessage.substring(7).split(":");
        if (parts.length < 2) {
            System.out.println("Invalid authorization format. Please use !autho!login:password");
            return false;
        }
        String login = parts[0];
        String password = parts[1];
        try {
            user = userDao.findByNameAndPassword(login, password);
            return true;
        } catch (UserNotFoundException e) {
            System.out.println("Authorization failed: " + e.getMessage());
            return false;
        }
    }

    @SneakyThrows
    @Override
    public void notifyMe(String messege) {
        PrintWriter clienWtitter = new PrintWriter(socket.getOutputStream());
        clienWtitter.println(messege);
        clienWtitter.flush();

    }
    @SneakyThrows
    private boolean register(String registrationMessage) {
        // Парсинг сообщения для регистрации
        String[] parts = registrationMessage.substring(10).split(":");
        if (parts.length < 2) {
            System.out.println("Invalid registration format. Please use !register!login:password");
            return false;
        }
        String login = parts[0];
        String password = parts[1];
        try {
            userDao.insertUser(login, password); // Вставка нового пользователя в базу данных
            user = new User(login, password); // Создание объекта User для текущего клиента
            return true; // Если регистрация успешна возвращаем true
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage()); // Если регистрация не удалась выводим сообщение об ошибке
            return false;
        }
    }
    }

