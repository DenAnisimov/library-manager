package org.example.dao;

import org.example.entity.BookGenre;

import java.sql.SQLException;

public interface BookGenreDAO {
    BookGenre getByBookId(int bookId) throws SQLException;

    BookGenre getByGenreId(int genreId) throws SQLException;
}
