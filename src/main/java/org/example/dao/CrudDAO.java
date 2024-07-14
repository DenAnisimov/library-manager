package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> {
    List<T> getAll() throws SQLException;

    T getById(int id) throws SQLException;

    void insert(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int id) throws SQLException;
}
