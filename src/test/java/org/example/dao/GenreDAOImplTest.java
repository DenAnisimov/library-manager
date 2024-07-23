package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Genre;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreDAOImplTest {

    @Mock
    private DataBaseConnection dataBaseConnection;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private GenreDAOImpl genreDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        when(dataBaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        genreDAO = new GenreDAOImpl(dataBaseConnection);
    }

    @Test
    void testGetAll() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2, 3);
        when(resultSet.getString("name")).thenReturn("Genre 1", "Genre 2", "Genre 3");

        List<Genre> genres = genreDAO.getAll();

        Genre genre = genres.getFirst();

        assertEquals(3, genres.size(), "Expected 3 genres");
        assertEquals(1, genre.getId(), "Expected id 1");
        assertEquals("Genre 1", genre.getName(), "Expected Genre 1");

        verify(resultSet, times(4)).next();
        verify(preparedStatement).close();
        verify(connection).close();
        verify(resultSet).close();
    }

    @Test
    void testGetById() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Genre 1");

        Genre genre = genreDAO.getById(1);

        assertEquals(1, genre.getId(), "Expected id 1");
        assertEquals("Genre 1", genre.getName(), "Expected Genre 1");

        verify(resultSet).next();
        verify(preparedStatement).close();
        verify(connection).close();
        verify(resultSet).close();
    }

    @Test
    void testInsert() throws SQLException {
        Genre genre = new Genre.Builder()
                .name("Genre 1")
                .build();

        genreDAO.update(genre);

        verify(preparedStatement).setString(1, genre.getName());
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testUpdate() throws SQLException {
        Genre genre = new Genre.Builder()
                .id(1)
                .name("Genre 1")
                .build();

        genreDAO.update(genre);

        verify(preparedStatement).setString(1, genre.getName());
        verify(preparedStatement).setInt(2, genre.getId());
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testDelete() throws SQLException {
        int genreId = 1;

        genreDAO.delete(genreId);

        verify(preparedStatement).setInt(1, genreId);
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(connection).close();
    }
}
