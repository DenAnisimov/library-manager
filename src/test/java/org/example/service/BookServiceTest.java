package org.example.service;

import org.example.dao.BookDAO;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.entity.AuthorDetails;
import org.example.entity.Book;
import org.example.entity.Author;
import org.example.map.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookDAO bookDAO;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws SQLException {
        int id = 1;
        Author author = new Author(1, "Author Name", new AuthorDetails());
        Book book = new Book.Builder()
                .id(id)
                .title("Book Title")
                .description("Book Description")
                .author(author)
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();
        BookDTO bookDTO = new BookDTO.Builder()
                .id(id)
                .title("Book Title")
                .description("Book Description")
                .author(new AuthorDTO.Builder().id(author.getId()).name(author.getName()).build())
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();

        when(bookDAO.getById(id)).thenReturn(book);
        when(bookMapper.mapToDTO(book)).thenReturn(bookDTO);

        BookDTO result = bookService.findById(id);

        assertEquals(bookDTO, result);
        verify(bookDAO).getById(id);
        verify(bookMapper).mapToDTO(book);
    }

    @Test
    void testFindAll() throws SQLException {
        Author author = new Author(1, "Author Name", new AuthorDetails());
        List<Book> books = List.of(new Book.Builder()
                .id(1)
                .title("Book Title")
                .description("Book Description")
                .author(author)
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build());
        List<BookDTO> bookDTOs = List.of(new BookDTO.Builder()
                .id(1)
                .title("Book Title")
                .description("Book Description")
                .author(new AuthorDTO.Builder().id(author.getId()).name(author.getName()).build())
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build());

        when(bookDAO.getAll()).thenReturn(books);
        when(bookMapper.mapToDTO(books)).thenReturn(bookDTOs);

        List<BookDTO> result = bookService.findAll();

        assertEquals(bookDTOs, result);
        verify(bookDAO).getAll();
        verify(bookMapper).mapToDTO(books);
    }

    @Test
    void testFindAllByAuthor() throws SQLException {
        int authorId = 1;
        Author author = new Author(1, "Author Name", new AuthorDetails());
        List<Book> books = List.of(new Book.Builder()
                .id(1)
                .title("Book Title")
                .description("Book Description")
                .author(author)
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build());
        List<BookDTO> bookDTOs = List.of(new BookDTO.Builder()
                .id(1)
                .title("Book Title")
                .description("Book Description")
                .author(new AuthorDTO.Builder().id(author.getId()).name(author.getName()).build())
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build());

        when(bookDAO.getAllByAuthor(authorId)).thenReturn(books);
        when(bookMapper.mapToDTO(books)).thenReturn(bookDTOs);

        List<BookDTO> result = bookService.findAllByAuthor(authorId);

        assertEquals(bookDTOs, result);
        verify(bookDAO).getAllByAuthor(authorId);
        verify(bookMapper).mapToDTO(books);
    }

    @Test
    void testDelete() throws SQLException {
        int id = 1;

        bookService.delete(id);

        verify(bookDAO).delete(id);
    }

    @Test
    void testUpdate() throws SQLException {
        BookDTO bookDTO = new BookDTO.Builder()
                .id(1)
                .title("Updated Title")
                .description("Updated Description")
                .author(new AuthorDTO.Builder().id(1).name("Author Name").build())
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();
        Book book = new Book.Builder()
                .id(1)
                .title("Updated Title")
                .description("Updated Description")
                .author(new Author(1, "Author Name", new AuthorDetails()))
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();

        when(bookMapper.mapToEntity(bookDTO)).thenReturn(book);

        bookService.update(bookDTO);

        verify(bookDAO).update(book);
    }

    @Test
    void testAdd() throws SQLException {
        BookDTO bookDTO = new BookDTO.Builder()
                .id(1)
                .title("New Title")
                .description("New Description")
                .author(new AuthorDTO.Builder().id(1).name("Author Name").build())
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();
        Book book = new Book.Builder()
                .id(1)
                .title("New Title")
                .description("New Description")
                .author(new Author(1, "Author Name", new AuthorDetails()))
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();

        when(bookMapper.mapToEntity(bookDTO)).thenReturn(book);

        bookService.add(bookDTO);

        verify(bookDAO).insert(book);
    }

    // Tests for exceptions

    @Test
    void testFindByIdSQLException() throws SQLException {
        int id = 1;
        when(bookDAO.getById(id)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> bookService.findById(id));

        verify(bookDAO).getById(id);
        verifyNoInteractions(bookMapper);
    }

    @Test
    void testFindAllSQLException() throws SQLException {
        when(bookDAO.getAll()).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> bookService.findAll());

        verify(bookDAO).getAll();
        verifyNoInteractions(bookMapper);
    }

    @Test
    void testFindAllByAuthorSQLException() throws SQLException {
        int authorId = 1;
        when(bookDAO.getAllByAuthor(authorId)).thenThrow(SQLException.class);

        assertThrows(RuntimeException.class, () -> bookService.findAllByAuthor(authorId));

        verify(bookDAO).getAllByAuthor(authorId);
        verifyNoInteractions(bookMapper);
    }

    @Test
    void testDeleteSQLException() throws SQLException {
        int id = 1;
        doThrow(SQLException.class).when(bookDAO).delete(id);

        assertThrows(RuntimeException.class, () -> bookService.delete(id));

        verify(bookDAO).delete(id);
    }

    @Test
    void testUpdateSQLException() throws SQLException {
        BookDTO bookDTO = new BookDTO.Builder()
                .id(1)
                .title("Updated Title")
                .description("Updated Description")
                .author(new AuthorDTO.Builder().id(1).name("Author Name").build())
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();
        Book book = new Book.Builder()
                .id(1)
                .title("Updated Title")
                .description("Updated Description")
                .author(new Author(1, "Author Name", new AuthorDetails()))
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();

        when(bookMapper.mapToEntity(bookDTO)).thenReturn(book);
        doThrow(SQLException.class).when(bookDAO).update(book);

        assertThrows(RuntimeException.class, () -> bookService.update(bookDTO));

        verify(bookDAO).update(book);
    }

    @Test
    void testAddSQLException() throws SQLException {
        BookDTO bookDTO = new BookDTO.Builder()
                .id(1)
                .title("New Title")
                .description("New Description")
                .author(new AuthorDTO.Builder().id(1).name("Author Name").build())
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();
        Book book = new Book.Builder()
                .id(1)
                .title("New Title")
                .description("New Description")
                .author(new Author(1, "Author Name", new AuthorDetails()))
                .publicationDate(LocalDate.of(2024, 7, 27))
                .build();

        when(bookMapper.mapToEntity(bookDTO)).thenReturn(book);
        doThrow(SQLException.class).when(bookDAO).insert(book);

        assertThrows(RuntimeException.class, () -> bookService.add(bookDTO));

        verify(bookDAO).insert(book);
    }
}
