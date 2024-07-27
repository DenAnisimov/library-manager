package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Author;
import org.example.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookDAOImplTest {

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
    private BookDAOImpl bookDAOImpl;

    @BeforeEach
    void setUp() throws SQLException {
        when(dataBaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        bookDAOImpl = new BookDAOImpl(dataBaseConnection, authorDAO);
    }

    @Test
    void testGetAll() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2, 3);
        when(resultSet.getString("title"))
                .thenReturn("Book Title 1", "Book Title 2", "Book Title 3");
        when(resultSet.getString("description"))
                .thenReturn("Book Description 1", "Book Description 2", "Book Description 3");
        when(resultSet.getDate("publication_date"))
                .thenReturn(Date.valueOf(LocalDate.of(1990, 1, 1)),
                        Date.valueOf(LocalDate.of(1990, 1, 2)),
                        Date.valueOf(LocalDate.of(1990, 1, 3)));
        when(resultSet.getInt("author_id")).thenReturn(1, 2, 3);

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

        List<Book> books = bookDAOImpl.getAll();

        Book book = books.getFirst();

        assertEquals(3, books.size(), "Expected 3 Books");
        assertEquals(1, book.getId(), "Expected id 1");
        assertEquals("Book Title 1", book.getTitle(), "Expected Book Title 1");
        assertEquals("Book Description 1", book.getDescription(), "Expected Book Description 1");
        assertEquals(author1, book.getAuthor(), "Expected Author 1");

        verify(authorDAO, times(3)).getById(anyInt());
        verify(preparedStatement).close();
        verify(connection).close();
        verify(resultSet).close();
    }

    @Test
    void testGetByAuthor() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2, 3);
        when(resultSet.getString("title"))
                .thenReturn("Book Title 1", "Book Title 2", "Book Title 2");
        when(resultSet.getString("description"))
                .thenReturn("Book Description 1", "Book Description 2", "Book Description 3");
        when(resultSet.getDate("publication_date"))
                .thenReturn(Date.valueOf(LocalDate.of(1990, 1, 1)),
                        Date.valueOf(LocalDate.of(1990, 1, 2)),
                        Date.valueOf(LocalDate.of(1990, 1, 3)));
        when(resultSet.getInt("author_id")).thenReturn(1, 1, 1);

        Author author1 = new Author.Builder()
                .id(1)
                .name("Author 1")
                .build();

        when(authorDAO.getById(anyInt())).thenReturn(author1, author1, author1);

        List<Book> booksByAuthor = bookDAOImpl.getAllByAuthor(author1.getId());

        Book firstBook = booksByAuthor.getFirst();
        Book secondBook = booksByAuthor.get(1);
        Book thirdBook = booksByAuthor.get(2);

        assertEquals(3, booksByAuthor.size(), "Expected 3 Books");
        assertEquals(1, firstBook.getId(), "Expected id_author 1");
        assertEquals(author1, firstBook.getAuthor(), "Expected Author 1");
        assertEquals(author1, secondBook.getAuthor(), "Expected Author 1");
        assertEquals(author1, thirdBook.getAuthor(), "Expected Author 1");

        verify(authorDAO, times(3)).getById(anyInt());
        verify(preparedStatement).setInt(1, author1.getId());
        verify(preparedStatement).close();
        verify(connection).close();
        verify(resultSet).close();
    }

    @Test
    void testGetById() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title"))
                .thenReturn("Book Title 1");
        when(resultSet.getString("description"))
                .thenReturn("Book Description 1");
        when(resultSet.getDate("publication_date"))
                .thenReturn(Date.valueOf(LocalDate.of(1990, 1, 1)));
        when(resultSet.getInt("author_id")).thenReturn(1);

        Author author = new Author.Builder()
                .id(1)
                .name("Author 1")
                .build();

        when(authorDAO.getById(anyInt())).thenReturn(author);

        int bookId = 1;
        Book book = bookDAOImpl.getById(bookId);

        assertEquals(bookId, book.getId(), "Expected id 1");
        assertEquals("Book Title 1", book.getTitle(), "Expected Book Title 1");
        assertEquals("Book Description 1", book.getDescription(), "Expected Book Description 1");
        assertEquals(author, book.getAuthor(), "Expected Author 1");

        verify(preparedStatement).setInt(1, bookId);
        verify(authorDAO).getById(anyInt());
        verify(preparedStatement).close();
        verify(connection).close();
        verify(resultSet).close();
    }

    @Test
    void testInsertWithoutAuthor() throws SQLException {
        Book book = new Book.Builder()
                .id(1)
                .title("Book title")
                .description("Book description")
                .publicationDate(LocalDate.now())
                .build();

        bookDAOImpl.insert(book);

        verify(preparedStatement).setString(1, book.getTitle());
        verify(preparedStatement).setString(2, book.getDescription());
        verify(preparedStatement).setInt(3,0);
        verify(preparedStatement).setDate(4, Date.valueOf(book.getPublicationDate()));
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testInsert() throws SQLException {
        Author author = new Author.Builder()
                .id(1)
                .name("Author 1")
                .build();
        Book book = new Book.Builder()
                .id(1)
                .title("Book title")
                .description("Book description")
                .publicationDate(LocalDate.now())
                .author(author)
                .build();

        bookDAOImpl.insert(book);

        verify(preparedStatement).setString(1, book.getTitle());
        verify(preparedStatement).setString(2, book.getDescription());
        verify(preparedStatement).setInt(3, author.getId());
        verify(preparedStatement).setDate(4, Date.valueOf(book.getPublicationDate()));
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testUpdateWithoutAuthor() throws SQLException {
        Book book = new Book.Builder()
                .id(1)
                .title("Book title")
                .description("Book description")
                .publicationDate(LocalDate.now())
                .build();

        bookDAOImpl.update(book);

        verify(preparedStatement).setString(1, book.getTitle());
        verify(preparedStatement).setString(2, book.getDescription());
        verify(preparedStatement).setInt(3,0);
        verify(preparedStatement).setDate(4, Date.valueOf(book.getPublicationDate()));
        verify(preparedStatement).setInt(5, book.getId());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testUpdate() throws SQLException {
        Author author = new Author.Builder()
                .id(1)
                .name("Author 1")
                .build();
        Book book = new Book.Builder()
                .id(1)
                .title("Book title")
                .description("Book description")
                .publicationDate(LocalDate.now())
                .author(author)
                .build();

        bookDAOImpl.update(book);

        verify(preparedStatement).setString(1, book.getTitle());
        verify(preparedStatement).setString(2, book.getDescription());
        verify(preparedStatement).setInt(3, author.getId());
        verify(preparedStatement).setDate(4, Date.valueOf(book.getPublicationDate()));
        verify(preparedStatement).setInt(5, book.getId());
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement).close();
        verify(connection).close();
    }

    @Test
    void testDelete() throws SQLException {
        int bookId = 1;

        bookDAOImpl.delete(bookId);

        verify(preparedStatement).setInt(1, bookId);
        verify(preparedStatement).executeUpdate();

        verify(preparedStatement).close();
        verify(connection).close();
    }
}
