package iplm.interfaces.observer;

public interface IObserver<T> {
    void update(T subject);
    boolean isActive();
}
