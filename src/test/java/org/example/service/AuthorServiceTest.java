package org.example.service;

import org.example.dao.AuthorDAO;
import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.map.AuthorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorDAO authorDAO;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() throws SQLException {
        String name = "Author Name";
        AuthorDTO authorDTO = new AuthorDTO.Builder()
                .name(name)
                .build();
        Author author = authorMapper.mapToEntity(authorDTO);

        when(authorMapper.mapToEntity(authorDTO)).thenReturn(author);

        authorService.add(name);

        verify(authorMapper, times(2)).mapToEntity(authorDTO);
        verify(authorDAO).insert(author);
    }

    @Test
    void testFindAll() throws SQLException {
        List<Author> authors = new ArrayList<>();
        List<AuthorDTO> authorDTOs = new ArrayList<>();

        when(authorDAO.getAll()).thenReturn(authors);
        when(authorMapper.mapToDTO(authors)).thenReturn(authorDTOs);

        List<AuthorDTO> result = authorService.findAll();

        assertEquals(authorDTOs, result);
        verify(authorDAO).getAll();
        verify(authorMapper).mapToDTO(authors);
    }

    @Test
    void testFindById() throws SQLException {
        int id = 1;
        Author author = new Author();
        AuthorDTO authorDTO = new AuthorDTO();

        when(authorDAO.getById(id)).thenReturn(author);
        when(authorMapper.mapToDTO(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.findById(id);

        assertEquals(authorDTO, result);
        verify(authorDAO).getById(id);
        verify(authorMapper).mapToDTO(author);
    }

    @Test
    void testDelete() throws SQLException {
        int id = 1;

        authorService.delete(id);

        verify(authorDAO).delete(id);
    }

    @Test
    void testUpdate() throws SQLException {
        AuthorDTO authorDTO = new AuthorDTO();
        Author author = authorMapper.mapToEntity(authorDTO);

        when(authorMapper.mapToEntity(authorDTO)).thenReturn(author);

        authorService.update(authorDTO);

        verify(authorMapper, times(2)).mapToEntity(authorDTO);
        verify(authorDAO).update(author);
    }

    @Test
    void testAddSQLException() throws SQLException {
        String name = "Author Name";
        AuthorDTO authorDTO = new AuthorDTO.Builder()
                .name(name)
                .build();
        Author author = authorMapper.mapToEntity(authorDTO);

        when(authorMapper.mapToEntity(authorDTO)).thenReturn(author);
        doThrow(SQLException.class).when(authorDAO).insert(author);

        assertThrows(RuntimeException.class, () -> authorService.add(name));

        verify(authorMapper, times(2)).mapToEntity(authorDTO);
        verify(authorDAO).insert(author);
    }

    @Test
    void testFindAllSQLException() throws SQLException {
        when(authorDAO.getAll()).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> authorService.findAll());

        verify(authorDAO).getAll();
        verifyNoInteractions(authorMapper);
    }

    @Test
    void testFindByIdSQLException() throws SQLException {
        int id = 1;
        when(authorDAO.getById(id)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> authorService.findById(id));

        verify(authorDAO).getById(id);
        verifyNoInteractions(authorMapper);
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        int id = 1;
        doThrow(SQLException.class).when(authorDAO).delete(id);

        assertThrows(RuntimeException.class, () -> authorService.delete(id));

        verify(authorDAO).delete(id);
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        AuthorDTO authorDTO = new AuthorDTO();
        Author author = authorMapper.mapToEntity(authorDTO);

        when(authorMapper.mapToEntity(authorDTO)).thenReturn(author);
        doThrow(SQLException.class).when(authorDAO).update(author);

        assertThrows(RuntimeException.class, () -> authorService.update(authorDTO));

        verify(authorMapper, times(2)).mapToEntity(authorDTO);
        verify(authorDAO).update(author);
    }
}
