package iplm.interfaces.repository;

import java.util.List;

public interface IRepository<T> {
    boolean add(T entity);
    boolean remove(T entity);
    T findById(int id);
    List<T> getAll();
}
