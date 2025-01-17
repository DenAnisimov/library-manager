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
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorDAOImplTest {

    @Mock
    private DataBaseConnection dataBaseConnection;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private AuthorDAOImpl authorDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(dataBaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        authorDAO = new AuthorDAOImpl(dataBaseConnection);
    }

    @Test
    void testGetAll() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("author_id")).thenReturn(1, 2);
        when(resultSet.getString("author_name")).thenReturn("Author Name 1", "Author Name 2");
        when(resultSet.getInt("author_details_id")).thenReturn(1, 2);
        when(resultSet.getString("author_details_life_years")).thenReturn("1900-2000", "1990-н.в.");
        when(resultSet.getString("author_details_brief_biography"))
                .thenReturn("A brief bio 1", "A brief bio 2");

        List<Author> authors = authorDAO.getAll();

        assertEquals(2, authors.size(), "Expected two author in the result list");
        Author author = authors.getFirst();
        assertEquals(1, author.getId(), "Expected author ID to be 1");
        assertEquals("Author Name 1", author.getName(), "Expected author name to be 'Author Name'");

        AuthorDetails authorDetails = author.getAuthorDetails();
        assertNotNull(authorDetails, "Expected author details to be present");
        assertEquals("1900-2000", authorDetails.getLifeYears(),
                "Expected life years to be '1900-2000'");
        assertEquals("A brief bio 1", authorDetails.getBriefBiography(),
                "Expected brief biography to be 'A brief bio'");

        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
        verify(resultSet).close();
    }

    @Test
    void testGetById() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("author_id")).thenReturn(1);
        when(resultSet.getString("author_name")).thenReturn("Author Name 1");
        when(resultSet.getInt("author_details_id")).thenReturn(1);
        when(resultSet.getString("author_details_life_years")).thenReturn("1900-2000");
        when(resultSet.getString("author_details_brief_biography"))
                .thenReturn("A brief bio 1");

        Author author = authorDAO.getById(1);

        assertEquals(1, author.getId(), "Expected author ID to be 1");
        assertEquals("Author Name 1", author.getName(), "Expected author name to be 'Author Name'");

        AuthorDetails authorDetails = author.getAuthorDetails();

        assertNotNull(authorDetails, "Expected author details to be present");

        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
        verify(resultSet).close();
    }

    @Test
    void testInsert() throws SQLException {
        Author author = new Author.Builder()
                .name("Author")
                .build();

        authorDAO.insert(author);

        verify(dataBaseConnection).getConnection();
        verify(preparedStatement).setString(1, author.getName());
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testUpdate() throws SQLException {
        Author author = new Author.Builder()
                .id(5)
                .name("Updated author")
                .build();

        authorDAO.update(author);

        verify(dataBaseConnection).getConnection();
        verify(preparedStatement).setString(1, author.getName());
        verify(preparedStatement).setInt(2, author.getId());
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testDelete() throws SQLException {
        int authorId = 1;

        authorDAO.delete(authorId);

        verify(dataBaseConnection).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setInt(1, authorId);
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }
}
