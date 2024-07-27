package org.example.service;

import org.example.dao.BookGenreDAO;
import org.example.dto.BookGenreDTO;
import org.example.entity.BookGenre;
import org.example.map.BookGenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookGenreServiceTest {

    @Mock
    private BookGenreDAO bookGenreDAO;

    @Mock
    private BookGenreMapper bookGenreMapper;

    @InjectMocks
    private BookGenreService bookGenreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() throws SQLException {
        BookGenre bookGenres = new BookGenre();
        BookGenreDTO bookGenreDTO = new BookGenreDTO();

        when(bookGenreDAO.getAll()).thenReturn(bookGenres);
        when(bookGenreMapper.mapToDTO(bookGenres)).thenReturn(bookGenreDTO);

        BookGenreDTO result = bookGenreService.getAll();

        assertEquals(bookGenreDTO, result);
        verify(bookGenreDAO).getAll();
        verify(bookGenreMapper).mapToDTO(bookGenres);
    }

    @Test
    void testGetAllByGenreId() throws SQLException {
        int genreId = 1;
        BookGenre bookGenres = new BookGenre();
        BookGenreDTO bookGenreDTO = new BookGenreDTO();

        when(bookGenreDAO.getByGenreId(genreId)).thenReturn(bookGenres);
        when(bookGenreMapper.mapToDTO(bookGenres)).thenReturn(bookGenreDTO);

        BookGenreDTO result = bookGenreService.getAllByGenreId(genreId);

        assertEquals(bookGenreDTO, result);
        verify(bookGenreDAO).getByGenreId(genreId);
        verify(bookGenreMapper).mapToDTO(bookGenres);
    }

    @Test
    void testGetAllByBookId() throws SQLException {
        int bookId = 1;
        BookGenre bookGenres = new BookGenre();
        BookGenreDTO bookGenreDTO = new BookGenreDTO();

        when(bookGenreDAO.getByBookId(bookId)).thenReturn(bookGenres);
        when(bookGenreMapper.mapToDTO(bookGenres)).thenReturn(bookGenreDTO);

        BookGenreDTO result = bookGenreService.getAllByBookId(bookId);

        assertEquals(bookGenreDTO, result);
        verify(bookGenreDAO).getByBookId(bookId);
        verify(bookGenreMapper).mapToDTO(bookGenres);
    }

    @Test
    void testAdd() throws SQLException {
        int bookId = 1;
        int genreId = 1;

        bookGenreService.add(bookId, genreId);

        verify(bookGenreDAO).insert(bookId, genreId);
    }

    @Test
    void testDelete() throws SQLException {
        int bookId = 1;
        int genreId = 1;

        bookGenreService.delete(bookId, genreId);

        verify(bookGenreDAO).delete(bookId, genreId);
    }

    @Test
    void testGetAllSQLException() throws SQLException {
        when(bookGenreDAO.getAll()).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> bookGenreService.getAll());

        verify(bookGenreDAO).getAll();
        verifyNoInteractions(bookGenreMapper);
    }

    @Test
    void testGetAllByGenreIdSQLException() throws SQLException {
        int genreId = 1;
        when(bookGenreDAO.getByGenreId(genreId)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> bookGenreService.getAllByGenreId(genreId));

        verify(bookGenreDAO).getByGenreId(genreId);
        verifyNoInteractions(bookGenreMapper);
    }

    @Test
    void testGetAllByBookIdSQLException() throws SQLException {
        int bookId = 1;
        when(bookGenreDAO.getByBookId(bookId)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> bookGenreService.getAllByBookId(bookId));

        verify(bookGenreDAO).getByBookId(bookId);
        verifyNoInteractions(bookGenreMapper);
    }

    @Test
    void testAddSQLException() throws SQLException {
        int bookId = 1;
        int genreId = 1;
        doThrow(SQLException.class).when(bookGenreDAO).insert(bookId, genreId);

        assertThrows(RuntimeException.class, () -> bookGenreService.add(bookId, genreId));

        verify(bookGenreDAO).insert(bookId, genreId);
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        int bookId = 1;
        int genreId = 1;
        doThrow(SQLException.class).when(bookGenreDAO).delete(bookId, genreId);

        assertThrows(RuntimeException.class, () -> bookGenreService.delete(bookId, genreId));

        verify(bookGenreDAO).delete(bookId, genreId);
    }
}