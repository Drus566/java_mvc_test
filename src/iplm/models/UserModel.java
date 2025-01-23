package iplm.models;

import iplm.data.User;
import iplm.interfaces.observer.IObservable;
import iplm.service.UserService;
import iplm.interfaces.observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class UserModel implements IObservable<User> {
    private List<IObserver<User>> observers = new ArrayList<>();
    private UserService service;

    // Конструкторы, геттеры и сеттеры
    public UserModel(UserService service) {
        this.service = service;
    }

    public static List<String> getNames() {
        return null;
    }

    public int getId() {
        return 0;
    }

    @Override
    public void addObserver(IObserver<User> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver<User> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }
}
