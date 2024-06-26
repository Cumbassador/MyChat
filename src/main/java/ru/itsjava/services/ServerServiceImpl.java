package ru.itsjava.services;

import dao.UserDao;
import dao.UserDaoImpl;
import lombok.SneakyThrows;
import ru.itsjava.utils.Props;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerServiceImpl implements ServerService{
    public final static int PORT = 8081;
    public static List<Observer> observers = new ArrayList<>();
    private final UserDao userDao = new UserDaoImpl(new Props());
    @SneakyThrows
    @Override
    public void start(){
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("SERVER START");

        while (true){
            Socket socket = serverSocket.accept();
            if (socket != null) {
                Thread thread = new Thread(new ClientRunnable(socket,this,userDao));
                thread.start();

            }

        }

    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);

    }

    @Override
    public void delerteObserver(Observer observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyObservers(String messege) {
        for(Observer observer: observers){
            observer.notifyMe(messege);
        }

    }
    public void notifyObserversExceptMe(String messege, Observer sender) {
        for (Observer observer : observers) {
            if (!observer.equals(sender)) {
                observer.notifyMe(messege);
            }
        }
    }
    public UserDao getUserDao() {
        return userDao;
    }



}
