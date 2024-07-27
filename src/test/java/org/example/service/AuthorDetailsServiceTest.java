package org.example.service;

import org.example.dao.AuthorDAO;
import org.example.dao.AuthorDetailsDAO;
import org.example.dto.AuthorDetailsDTO;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
import org.example.map.AuthorDetailsMapper;
import org.example.map.AuthorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorDetailsServiceTest {

    @Mock
    private AuthorDetailsDAO authorDetailsDAO;

    @Mock
    private AuthorDAO authorDAO;

    @Mock
    private AuthorDetailsMapper authorDetailsMapper;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorDetailsService authorDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() throws SQLException {
        int authorId = 1;
        Author author = new Author();
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO.Builder()
                .briefBiography("Brief Bio")
                .lifeYears("1900-2000")
                .authorDTO(authorMapper.mapToDTO(author))
                .build();
        AuthorDetails authorDetails = authorDetailsMapper.mapToEntity(authorDetailsDTO);

        when(authorDAO.getById(authorId)).thenReturn(author);
        when(authorDetailsMapper.mapToEntity(authorDetailsDTO)).thenReturn(authorDetails);

        authorDetailsService.add("Brief Bio", "1900-2000", authorId);

        verify(authorDAO).getById(authorId);
        verify(authorDetailsDAO).insert(authorDetails);
    }

    @Test
    void testFindAll() throws SQLException {
        List<AuthorDetails> authorDetailsList = new ArrayList<>();
        List<AuthorDetailsDTO> authorDetailsDTOList = new ArrayList<>();
        when(authorDetailsDAO.getAll()).thenReturn(authorDetailsList);
        when(authorDetailsMapper.mapToDTO(authorDetailsList)).thenReturn(authorDetailsDTOList);

        List<AuthorDetailsDTO> result = authorDetailsService.findAll();

        assertEquals(authorDetailsDTOList, result);
        verify(authorDetailsDAO).getAll();
    }

    @Test
    void testFindById() throws SQLException {
        int id = 1;
        AuthorDetails authorDetails = new AuthorDetails();
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        when(authorDetailsDAO.getById(id)).thenReturn(authorDetails);
        when(authorDetailsMapper.mapToDTO(authorDetails)).thenReturn(authorDetailsDTO);

        AuthorDetailsDTO result = authorDetailsService.findById(id);

        assertEquals(authorDetailsDTO, result);
        verify(authorDetailsDAO).getById(id);
    }

    @Test
    void testDelete() throws SQLException {
        int id = 1;

        authorDetailsService.delete(id);

        verify(authorDetailsDAO).delete(id);
    }

    @Test
    void testUpdate() throws SQLException {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        AuthorDetails authorDetails = authorDetailsMapper.mapToEntity(authorDetailsDTO);
        when(authorDetailsMapper.mapToEntity(authorDetailsDTO)).thenReturn(authorDetails);

        authorDetailsService.update(authorDetailsDTO);

        verify(authorDetailsDAO).update(authorDetails);
    }

    @Test
    void testAddSQLException() throws SQLException {
        int authorId = 1;
        when(authorDAO.getById(authorId)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> authorDetailsService.add("Brief Bio", "1900-2000", authorId));

        verify(authorDAO).getById(authorId);
        verifyNoInteractions(authorDetailsDAO);
    }

    @Test
    void testFindAllSQLException() throws SQLException {
        when(authorDetailsDAO.getAll()).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> authorDetailsService.findAll());

        verify(authorDetailsDAO).getAll();
        verifyNoInteractions(authorDetailsMapper);
    }

    @Test
    void testFindByIdSQLException() throws SQLException {
        int id = 1;
        when(authorDetailsDAO.getById(id)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> authorDetailsService.findById(id));

        verify(authorDetailsDAO).getById(id);
        verifyNoInteractions(authorDetailsMapper);
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        int id = 1;
        doThrow(SQLException.class).when(authorDetailsDAO).delete(id);

        assertThrows(RuntimeException.class, () -> authorDetailsService.delete(id));

        verify(authorDetailsDAO).delete(id);
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        AuthorDetails authorDetails = authorDetailsMapper.mapToEntity(authorDetailsDTO);
        when(authorDetailsMapper.mapToEntity(authorDetailsDTO)).thenReturn(authorDetails);
        doThrow(SQLException.class).when(authorDetailsDAO).update(authorDetails);

        assertThrows(RuntimeException.class, () -> authorDetailsService.update(authorDetailsDTO));

        verify(authorDetailsMapper, times(2)).mapToEntity(authorDetailsDTO);
        verify(authorDetailsDAO).update(authorDetails);
    }
}