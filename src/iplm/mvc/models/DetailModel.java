package iplm.mvc.models;

import iplm.data.repository.RepositoryType;
import iplm.data.types.Detail;
import iplm.data.types.DetailName;
import iplm.data.types.DetailParameterType;
import iplm.interfaces.observer.IObservable;
import iplm.interfaces.observer.IObserver;
import iplm.data.service.DetailService;

import java.util.ArrayList;
import java.util.List;

public class DetailModel implements IModel, IObservable<Detail> {
    private List<IObserver<Detail>> m_observers = new ArrayList<>();
    private DetailService m_service;

    public DetailModel() { m_service = new DetailService(RepositoryType.ORIENTDB); }

    public String addDetailName(String name) { return m_service.addDetailName(name); }
    public boolean deleteDetailName(String id) { return m_service.deleteDetailName(id); }
    public String updateDetailName(DetailName detail_name) { return m_service.updateDetailName(detail_name); }
    public ArrayList<DetailName> getDetailNames() { return m_service.getDetailNames(); }

    public String addDetailParameterType(DetailParameterType detail_parameter_type) { return m_service.addDetailParameterType(detail_parameter_type); }
    public boolean deleteDetailParameterType(String id) { return m_service.deleteDetailParameterType(id); }
    public String updateDetailParameterType(DetailParameterType detail_parameter_type) { return m_service.updateDetailParameterType(detail_parameter_type); }
    public ArrayList<DetailParameterType> getDetailParameterTypes() { return m_service.getDetailParameterTypes(); }

//    public ArrayList<Detail> getAll() { return m_service.getAll(); }
//
//    public Detail getById(String id) { return m_service.getById(id); }
//
//
//    /* get detail list */
//    public ArrayList<Detail> get(String request) { return m_service.get(request); }
//
//    /* return id */
//    public String add(Detail d) {
//        return m_service.add(d);
//    }
//
//    /* return id */
//    public String delete(String id) { return m_service.delete(id); }
//
//    /* return id */
//    public String update(Detail detail) { return m_service.update(detail); }
//
//    public boolean rebuildIndex() { return m_service.rebuildIndex(); }

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
