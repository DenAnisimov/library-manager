package org.example.dao;

import org.example.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO extends CrudDAO<Book> {
    List<Book> getAllByAuthor(int authorId) throws SQLException;
}
