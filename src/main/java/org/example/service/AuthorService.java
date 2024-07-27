package org.example.service;

import org.example.dao.AuthorDAO;
import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.map.AuthorMapper;

import java.sql.SQLException;
import java.util.List;

public class AuthorService {
    private final AuthorDAO authorDAO;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorDAO authorDAO, AuthorMapper authorMapper) {
        this.authorDAO = authorDAO;
        this.authorMapper = authorMapper;
    }

    public void add(String name) {
        AuthorDTO authorDTO = new AuthorDTO.Builder()
                .name(name)
                .build();
        Author author = authorMapper.mapToEntity(authorDTO);
        try {
            authorDAO.insert(author);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AuthorDTO> findAll() {
        try {
            return authorMapper.mapToDTO(authorDAO.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthorDTO findById(int id) {
        try {
            return authorMapper.mapToDTO(authorDAO.getById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            authorDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(AuthorDTO authorDTO) {
        Author author = authorMapper.mapToEntity(authorDTO);
        try {
            authorDAO.update(author);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}