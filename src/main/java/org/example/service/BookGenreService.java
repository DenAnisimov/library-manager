package org.example.service;

import org.example.dao.BookGenreDAO;
import org.example.dto.BookGenreDTO;
import org.example.map.BookGenreMapper;

import java.sql.SQLException;

public class BookGenreService {
    private BookGenreDAO bookGenreDAO;
    private BookGenreMapper bookGenreMapper;
    public BookGenreService(BookGenreDAO bookGenreDAO, BookGenreMapper bookGenreMapper) {
        this.bookGenreDAO = bookGenreDAO;
        this.bookGenreMapper = bookGenreMapper;
    }

    public BookGenreDTO getAll() {
        try {
            return bookGenreMapper.mapToDTO(bookGenreDAO.getAll());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public BookGenreDTO getAllByGenreId(int genreId) {
        try {
            return bookGenreMapper.mapToDTO(bookGenreDAO.getByGenreId(genreId));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public BookGenreDTO getAllByBookId(int bookId) {
        try {
            return bookGenreMapper.mapToDTO(bookGenreDAO.getByBookId(bookId));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void add(int bookId, int genreId) {
        try {
            bookGenreDAO.insert(bookId, genreId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(int bookId, int genreId) {
        try {
            bookGenreDAO.delete(bookId, genreId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
