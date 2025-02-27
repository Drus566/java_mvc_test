package iplm.mvc.models;

import iplm.data.repository.RepositoryType;
import iplm.data.types.Detail;
import iplm.interfaces.observer.IObservable;
import iplm.interfaces.observer.IObserver;
import iplm.data.service.DetailService;

import java.util.ArrayList;
import java.util.List;

public class DetailModel implements IModel, IObservable<Detail> {
    private List<IObserver<Detail>> m_observers = new ArrayList<>();
    private DetailService m_service;

    public DetailModel() { m_service = new DetailService(RepositoryType.ORIENTDB); }

    public ArrayList<Detail> getAll() { return m_service.getAll(); }

    public Detail getById(String id) { return m_service.getById(id); }

    /* get detail list */
    public ArrayList<Detail> get(String request) { return m_service.get(request); }

    /* return id */
    public String add(Detail d) {
        return m_service.add(d);
    }

    /* return id */
    public String remove(String id) {
        return m_service.remove(id);
    }

    /* return id */
    public String update(Detail d) {
        return m_service.update(d);
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
