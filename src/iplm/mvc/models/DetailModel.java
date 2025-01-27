package iplm.mvc.models;

import iplm.data.types.Detail;
import iplm.interfaces.observer.IObservable;
import iplm.interfaces.observer.IObserver;
import iplm.data.service.DetailService;

import java.util.ArrayList;
import java.util.List;

public class DetailModel implements IModel, IObservable<Detail> {
    private List<IObserver<Detail>> m_observers = new ArrayList<>();
    private DetailService m_service;

    public DetailModel() {
        m_service = new DetailService();
    }

    @Override
    public void addObserver(IObserver<Detail> observer) { m_observers.add(observer); }

    @Override
    public void removeObserver(IObserver<Detail> observer) {
        m_observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
//        for (IObserver<Detail> observer : observers) {
//            if (observer != null) observer.update();
//
//        }
    }

    @Override
    public void init() {

    }
}
