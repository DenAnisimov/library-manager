package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Book;
import org.example.entity.BookGenre;
import org.example.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookGenreDAOImplTest {

    @Mock
    private DataBaseConnection dataBaseConnection;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private BookGenreDAOImpl bookGenreDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(dataBaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        bookGenreDAO = new BookGenreDAOImpl(dataBaseConnection);
    }

    @Test
    void testGetByBookId() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getString("book_title")).thenReturn("Book Title");
        when(resultSet.getString("book_description")).thenReturn("Book Description");
        when(resultSet.getDate("book_publication_date")).thenReturn(Date.valueOf(LocalDate.of(2002, 6, 5)));
        when(resultSet.getInt("genre_id")).thenReturn(1, 2, 3);
        when(resultSet.getString("genre_name")).thenReturn("Genre 1", "Genre 2", "Genre 3");

        BookGenre bookGenre = bookGenreDAO.getByBookId(1);

        assertNotNull(bookGenre, "Ожидается ненулевой объект BookGenre");

        Set<Book> books = bookGenre.getBooks();
        assertEquals(1, books.size(), "Ожидается одна книга в результате");

        Book book = books.iterator().next();
        assertEquals(1, book.getId(), "Ожидается, что ID книги будет равен 1");
        assertEquals("Book Title", book.getTitle(), "Ожидается, что заголовок книги будет 'Book Title'");

        Set<Genre> genres = bookGenre.getGenres();
        assertEquals(3, genres.size(), "Ожидается три жанра в результате");

        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
    }

    @Test
    void testGetByBookIdWithNoResults() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        BookGenre bookGenre = bookGenreDAO.getByBookId(1);

        assertNull(bookGenre, "Expected null BookGenre object");

        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
    }

    @Test
    void testGetByBookIdWithSQLException() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            bookGenreDAO.getByBookId(1);
        }, "Expected getByBookId to throw, but it didn't");

        assertEquals("Database error", thrown.getMessage(), "Expected exception message to be 'Database error'");

        verify(connection).close();
        verify(preparedStatement).close();
    }

    @Test
    void testGetByGenreId() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getInt("genre_id")).thenReturn(1);
        when(resultSet.getString("genre_name")).thenReturn("Genre Name");
        when(resultSet.getInt("book_id")).thenReturn(1, 2, 3);
        when(resultSet.getString("book_title")).thenReturn("Book Title 1", "Book Title 2", "Book Title 3");
        when(resultSet.getString("book_description")).thenReturn("Book Description");
        when(resultSet.getDate("book_publication_date")).thenReturn(Date.valueOf(LocalDate.of(2002, 6, 5)));

        BookGenre bookGenre = bookGenreDAO.getByGenreId(1);

        assertNotNull(bookGenre, "Expected non-null BookGenre object");

        Set<Genre> genres = bookGenre.getGenres();
        assertEquals(1, genres.size(), "Expected one genre in the result set");

        Genre genre = genres.iterator().next();
        assertEquals(1, genre.getId(), "Expected genre ID to be 1");
        assertEquals("Genre Name", genre.getName(), "Expected genre name to be 'Genre Name'");

        Set<Book> books = bookGenre.getBooks();
        assertEquals(3, books.size(), "Expected three books in the result set");

        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
    }

    @Test
    void testGetByGenreIdWithNoBooks() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("genre_id")).thenReturn(1);
        when(resultSet.getString("genre_name")).thenReturn("Genre Name");

        BookGenre bookGenre = bookGenreDAO.getByGenreId(1);

        assertNotNull(bookGenre, "Expected non-null BookGenre object");

        Set<Genre> genres = bookGenre.getGenres();
        assertEquals(1, genres.size(), "Expected one genre in the result set");

        Genre genre = genres.iterator().next();
        assertEquals(1, genre.getId(), "Expected genre ID to be 1");
        assertEquals("Genre Name", genre.getName(), "Expected genre name to be 'Genre Name'");

        Set<Book> books = bookGenre.getBooks();
        assertEquals(0, books.size(), "Expected zero books in the result set");

        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
    }

    @Test
    void testGetByGenreIdWithNoResults() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(false);

        BookGenre bookGenre = bookGenreDAO.getByGenreId(1);

        assertNull(bookGenre, "Expected null BookGenre object");

        verify(connection).close();
        verify(preparedStatement).close();
        verify(resultSet).close();
    }

    @Test
    void testGetByGenreIdWithSQLException() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        SQLException thrown = assertThrows(SQLException.class, () -> {
            bookGenreDAO.getByGenreId(1);
        }, "Expected getByGenreId to throw, but it didn't");

        assertEquals("Database error", thrown.getMessage(), "Expected exception message to be 'Database error'");

        verify(connection).close();
        verify(preparedStatement).close();
    }
}
