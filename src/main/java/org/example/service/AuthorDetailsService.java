package org.example.service;

import org.example.dao.AuthorDAO;
import org.example.dao.AuthorDetailsDAO;
import org.example.dto.AuthorDetailsDTO;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
import org.example.map.AuthorDetailsMapper;
import org.example.map.AuthorMapper;

import java.sql.SQLException;
import java.util.List;

public class AuthorDetailsService {
    private final AuthorDetailsDAO authorDetailsDAO;
    private final AuthorDAO authorDAO;
    private final AuthorDetailsMapper authorDetailsMapper;
    private final AuthorMapper authorMapper;

    public AuthorDetailsService(AuthorDetailsDAO authorDetailsDAO, AuthorDAO authorDAO,
                                AuthorDetailsMapper authorDetailsMapper, AuthorMapper authorMapper) {
        this.authorDetailsDAO = authorDetailsDAO;
        this.authorDAO = authorDAO;
        this.authorDetailsMapper = authorDetailsMapper;
        this.authorMapper = authorMapper;
    }

    public void add(String briefBiography, String lifeYears, int authorId) {
        try {
            Author author = authorDAO.getById(authorId);
            AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO.Builder()
                    .briefBiography(briefBiography)
                    .lifeYears(lifeYears)
                    .authorDTO(authorMapper.mapToDTO(author))
                    .build();
            authorDetailsDAO.insert(authorDetailsMapper.mapToEntity(authorDetailsDTO));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AuthorDetailsDTO> findAll() {
        try {
            return authorDetailsMapper.mapToDTO(authorDetailsDAO.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthorDetailsDTO findById(int id) {
        try {
            return authorDetailsMapper.mapToDTO(authorDetailsDAO.getById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            authorDetailsDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(AuthorDetailsDTO authorDetailsDTO) {
        AuthorDetails author = authorDetailsMapper.mapToEntity(authorDetailsDTO);
        try {
            authorDetailsDAO.update(author);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
