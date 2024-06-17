package ru.itsjava.services;

import lombok.SneakyThrows;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerServiceImpl implements ServerService{
    public final static int PORT = 8081;
    public static List<Observer> observers = new ArrayList<>();
    @SneakyThrows
    @Override
    public void start(){
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("SERVER START");

        while (true){
            Socket socket = serverSocket.accept();
            if (socket != null) {
                Thread thread = new Thread(new ClientRunnable(socket,this));
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

}
