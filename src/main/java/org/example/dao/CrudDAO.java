package org.example.dao;

import java.util.List;

public interface CrudDAO<T> {
    List<T> getAll() throws Exception;

    T getById(int id) throws Exception;

    void insert(T entity) throws Exception;

    void update(T entity) throws Exception;

    void delete(int id) throws Exception;
}
