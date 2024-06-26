package ru.itsjava;


import dao.UserDao;
import dao.UserDaoImpl;
import ru.itsjava.services.ServerService;
import ru.itsjava.services.ServerServiceImpl;
import ru.itsjava.utils.Props;


public class Application {

    public static void main(String[] args)  {
        ServerService serverService = new ServerServiceImpl();
        serverService.start();
//        UserDaoImpl userDao = new UserDaoImpl(new Props());

        // Пример регистрации нового пользователя без обработки исключений
//        userDao.insertUser("newuser", "newpassword");
//        System.out.println("User registered successfully.");




    }
}
