package ru.itsjava.services;

import dao.UserDao;

public interface ServerService extends Observable {
    void start();

    @Override
    void addObserver(Observer observer);
    void delerteObserver(Observer observer);
    void notifyObservers(String message);
    void notifyObserversExceptMe(String message, Observer sender);
    UserDao getUserDao();
}
