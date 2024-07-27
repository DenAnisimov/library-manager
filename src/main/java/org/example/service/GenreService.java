package org.example.service;

import org.example.dao.GenreDAO;
import org.example.dto.GenreDTO;
import org.example.entity.Genre;
import org.example.map.GenreMapper;

import java.sql.SQLException;
import java.util.List;

public class GenreService {
    private final GenreDAO genreDAO;
    private final GenreMapper genreMapper;

    public GenreService(GenreDAO genreDAO, GenreMapper genreMapper) {
        this.genreDAO = genreDAO;
        this.genreMapper = genreMapper;
    }

    public void add(String name) {
        GenreDTO genreDTO = new GenreDTO.Builder()
                .name(name)
                .build();
        Genre genre = genreMapper.mapToEntity(genreDTO);
        try {
            genreDAO.insert(genre);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GenreDTO> findAll() {
        try {
            return genreMapper.mapToDTO(genreDAO.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GenreDTO findById(int id) {
        try {
            return genreMapper.mapToDTO(genreDAO.getById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            genreDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(GenreDTO genreDTO) {
        Genre genre = genreMapper.mapToEntity(genreDTO);
        try {
            genreDAO.update(genre);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}