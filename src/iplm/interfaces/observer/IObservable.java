package iplm.interfaces.observer;

public interface IObservable<T> {
    void addObserver(IObserver<T> observer);
    void removeObserver(IObserver<T> observer);
    void notifyObservers();
}
