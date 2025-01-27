package iplm.mvc.models;

import iplm.data.types.User;
import iplm.interfaces.observer.IObservable;
import iplm.data.repository.IUserRepository;
import iplm.interfaces.observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class UserModel implements IObservable<User> {
    private final IUserRepository userRepository;
    private List<IObserver<User>> observers = new ArrayList<>();

    // Конструкторы, геттеры и сеттеры
    public UserModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
