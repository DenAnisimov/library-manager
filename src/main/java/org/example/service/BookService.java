package org.example.service;

import org.example.dao.BookDAO;
import org.example.dto.BookDTO;
import org.example.map.BookMapper;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDAO bookDAO;
    private final BookMapper bookMapper;

    public BookService(BookDAO bookDAO, BookMapper bookMapper) {
        this.bookDAO = bookDAO;
        this.bookMapper = bookMapper;
    }

    public BookDTO findById(int id) {
        try {
            return bookMapper.mapToDTO(bookDAO.getById(id));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<BookDTO> findAll() {
        try {
            return bookMapper.mapToDTO(bookDAO.getAll());
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public List<BookDTO> findAllByAuthor(int authorId) {
        try {
            return bookMapper.mapToDTO(bookDAO.getAllByAuthor(authorId));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(int id) {
        try {
            bookDAO.delete(id);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(BookDTO book) {
        try {
            bookDAO.update(bookMapper.mapToEntity(book));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void add(BookDTO book) {
        try {
            bookDAO.insert(bookMapper.mapToEntity(book));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
