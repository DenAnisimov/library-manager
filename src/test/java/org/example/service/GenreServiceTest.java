package org.example.service;

import org.example.dao.GenreDAO;
import org.example.dto.GenreDTO;
import org.example.entity.Genre;
import org.example.map.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GenreServiceTest {

    @Mock
    private GenreDAO genreDAO;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() throws SQLException {
        String name = "Fiction";
        GenreDTO genreDTO = new GenreDTO.Builder()
                .name(name)
                .build();
        Genre genre = new Genre.Builder()
                .name(name)
                .build();

        when(genreMapper.mapToEntity(genreDTO)).thenReturn(genre);

        genreService.add(name);

        verify(genreMapper).mapToEntity(genreDTO);
        verify(genreDAO).insert(genre);
    }

    @Test
    void testFindAll() throws SQLException {
        List<Genre> genres = List.of(new Genre.Builder().name("Fiction").build());
        List<GenreDTO> genreDTOs = List.of(new GenreDTO.Builder().name("Fiction").build());

        when(genreDAO.getAll()).thenReturn(genres);
        when(genreMapper.mapToDTO(genres)).thenReturn(genreDTOs);

        List<GenreDTO> result = genreService.findAll();

        assertEquals(genreDTOs, result);
        verify(genreDAO).getAll();
        verify(genreMapper).mapToDTO(genres);
    }

    @Test
    void testFindById() throws SQLException {
        int id = 1;
        Genre genre = new Genre.Builder()
                .name("Fiction")
                .build();
        GenreDTO genreDTO = new GenreDTO.Builder()
                .name("Fiction")
                .build();

        when(genreDAO.getById(id)).thenReturn(genre);
        when(genreMapper.mapToDTO(genre)).thenReturn(genreDTO);

        GenreDTO result = genreService.findById(id);

        assertEquals(genreDTO, result);
        verify(genreDAO).getById(id);
        verify(genreMapper).mapToDTO(genre);
    }

    @Test
    void testDelete() throws SQLException {
        int id = 1;

        genreService.delete(id);

        verify(genreDAO).delete(id);
    }

    @Test
    void testUpdate() throws SQLException {
        GenreDTO genreDTO = new GenreDTO.Builder()
                .name("Science Fiction")
                .build();
        Genre genre = new Genre.Builder()
                .name("Science Fiction")
                .build();

        when(genreMapper.mapToEntity(genreDTO)).thenReturn(genre);

        genreService.update(genreDTO);

        verify(genreMapper).mapToEntity(genreDTO);
        verify(genreDAO).update(genre);
    }

    @Test
    void testAddSQLException() throws SQLException {
        String name = "Fiction";
        GenreDTO genreDTO = new GenreDTO.Builder()
                .name(name)
                .build();
        Genre genre = new Genre.Builder()
                .name(name)
                .build();

        when(genreMapper.mapToEntity(genreDTO)).thenReturn(genre);
        doThrow(SQLException.class).when(genreDAO).insert(genre);

        assertThrows(RuntimeException.class, () -> genreService.add(name));

        verify(genreMapper).mapToEntity(genreDTO);
        verify(genreDAO).insert(genre);
    }

    @Test
    void testFindAllSQLException() throws SQLException {
        when(genreDAO.getAll()).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> genreService.findAll());

        verify(genreDAO).getAll();
        verifyNoInteractions(genreMapper);
    }

    @Test
    void testFindByIdSQLException() throws SQLException {
        int id = 1;
        when(genreDAO.getById(id)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> genreService.findById(id));

        verify(genreDAO).getById(id);
        verifyNoInteractions(genreMapper);
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        int id = 1;
        doThrow(SQLException.class).when(genreDAO).delete(id);

        assertThrows(RuntimeException.class, () -> genreService.delete(id));

        verify(genreDAO).delete(id);
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        GenreDTO genreDTO = new GenreDTO.Builder()
                .name("Science Fiction")
                .build();
        Genre genre = new Genre.Builder()
                .name("Science Fiction")
                .build();

        when(genreMapper.mapToEntity(genreDTO)).thenReturn(genre);
        doThrow(SQLException.class).when(genreDAO).update(genre);

        assertThrows(RuntimeException.class, () -> genreService.update(genreDTO));

        verify(genreMapper).mapToEntity(genreDTO);
        verify(genreDAO).update(genre);
    }
}