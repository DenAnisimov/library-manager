package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorDetailsDAOImplTest {
    @Mock
    private DataBaseConnection dataBaseConnection;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private AuthorDAO authorDAO;
    @InjectMocks
    private AuthorDetailsDAOImpl authorDetailsDAO;

    @BeforeEach
    public void setUp() throws Exception {
        when(dataBaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        authorDetailsDAO = new AuthorDetailsDAOImpl(dataBaseConnection, authorDAO);
    }

    @Test
    void testGetAll() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 1, 2, 2, 3, 3);
        when(resultSet.getString("life_years")).thenReturn("1900-1980", "1959-1990", "1970-2020");
        when(resultSet.getString("brief_biography")).thenReturn("Brief biography 1", "Brief biography 2", "Brief biography 3");

        Author author1 = new Author.Builder()
                .id(1)
                .name("Author 1")
                .build();
        Author author2 = new Author.Builder()
                .id(2)
                .name("Author 2")
                .build();
        Author author3 = new Author.Builder()
                .id(3)
                .name("Author 3")
                .build();

        when(authorDAO.getById(anyInt())).thenReturn(author1, author2, author3);

        List<AuthorDetails> authorDetailsList = authorDetailsDAO.getAll();


        AuthorDetails authorDetails = authorDetailsList.getFirst();

        assertEquals(3, authorDetailsList.size(), "Expected 3 author details");
        assertEquals(author1, authorDetails.getAuthor(), "Expected author 1");
        assertEquals("Brief biography 1", authorDetails.getBriefBiography(), "Expected author 2");
        assertEquals("1900-1980", authorDetails.getLifeYears(), "Expected 1900-1980");
        assertEquals(1, authorDetails.getId(), "Expected id 1");

        verify(authorDAO, times(3)).getById(anyInt());
        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
    }

    @Test
    void testInsert() throws Exception {
        Author author = new Author.Builder()
                .id(5)
                .name("Author")
                .build();
        AuthorDetails authorDetails = new AuthorDetails.Builder()
                .id(5)
                .lifeYears("1900-1950")
                .briefBiography("Brief biography")
                .author(author)
                .build();

        authorDetailsDAO.insert(authorDetails);

        verify(connection).close();
        verify(preparedStatement).close();
    }

    @Test
    void testUpdate() throws Exception {
        Author author = new Author.Builder()
                .id(5)
                .name("Author")
                .build();
        AuthorDetails authorDetails = new AuthorDetails.Builder()
                .id(1)
                .author(author)
                .lifeYears("1900-1960")
                .briefBiography("Brief biography")
                .build();

        authorDetailsDAO.update(authorDetails);

        verify(preparedStatement).setString(1, authorDetails.getLifeYears());
        verify(preparedStatement).setString(2, authorDetails.getBriefBiography());
        verify(preparedStatement).setInt(3, authorDetails.getAuthor().getId());
        verify(preparedStatement).setInt(4, authorDetails.getId());
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testDelete() throws Exception {
        int authorDetailsId = 1;

        authorDetailsDAO.delete(authorDetailsId);

        verify(preparedStatement).setInt(1, authorDetailsId);
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }
}
