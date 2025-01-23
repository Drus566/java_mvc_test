package iplm.interfaces.repository;

import java.util.List;

public interface IRepository<T> {
    void add(T entity);
    void remove(T entity);
    T findById(int id);
    List<T> findAll();
}
