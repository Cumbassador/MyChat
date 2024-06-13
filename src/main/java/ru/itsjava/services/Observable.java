package ru.itsjava.services;

public interface Observable {
    void addObserver(Observer observer);
    void delerteObserver(Observer observer);

    void notifyObservers(String messege);

}
