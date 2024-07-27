package org.example.map;

import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.entity.Author;
import org.example.entity.Book;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Test
    void testMapToDTO() {
        Author author = new Author();
        author.setId(1);
        author.setName("Test Author");

        Book book = new Book.Builder()
                .id(1)
                .title("Test Book")
                .description("Test Description")
                .author(author)
                .publicationDate(LocalDate.of(2020, 1, 1))
                .build();

        BookDTO bookDTO = bookMapper.mapToDTO(book);

        assertNotNull(bookDTO);
        assertEquals(1, bookDTO.getId());
        assertEquals("Test Book", bookDTO.getTitle());
        assertEquals("Test Description", bookDTO.getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), bookDTO.getPublicationDate());

        AuthorDTO authorDTO = bookDTO.getAuthorDTO();
        assertNotNull(authorDTO);
        assertEquals(1, authorDTO.getId());
        assertEquals("Test Author", authorDTO.getName());
    }

    @Test
    void testMapToEntity() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Test Author");

        BookDTO bookDTO = new BookDTO.Builder()
                .id(1)
                .title("Test Book")
                .description("Test Description")
                .author(authorDTO)
                .publicationDate(LocalDate.of(2020, 1, 1))
                .build();

        Book book = bookMapper.mapToEntity(bookDTO);

        assertNotNull(book);
        assertEquals(1, book.getId());
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Description", book.getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), book.getPublicationDate());

        Author author = book.getAuthor();
        assertNotNull(author);
        assertEquals(1, author.getId());
        assertEquals("Test Author", author.getName());
    }

    @Test
    void testMapToDTOList() {
        Author author = new Author();
        author.setId(1);
        author.setName("Test Author");

        Book book1 = new Book.Builder()
                .id(1)
                .title("Test Book 1")
                .description("Test Description 1")
                .author(author)
                .publicationDate(LocalDate.of(2020, 1, 1))
                .build();

        Book book2 = new Book.Builder()
                .id(2)
                .title("Test Book 2")
                .description("Test Description 2")
                .author(author)
                .publicationDate(LocalDate.of(2021, 1, 1))
                .build();

        List<Book> books = Arrays.asList(book1, book2);
        List<BookDTO> bookDTOs = bookMapper.mapToDTO(books);

        assertNotNull(bookDTOs);
        assertEquals(2, bookDTOs.size());

        BookDTO bookDTO1 = bookDTOs.getFirst();
        assertEquals(1, bookDTO1.getId());
        assertEquals("Test Book 1", bookDTO1.getTitle());
        assertEquals("Test Description 1", bookDTO1.getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), bookDTO1.getPublicationDate());

        BookDTO bookDTO2 = bookDTOs.get(1);
        assertEquals(2, bookDTO2.getId());
        assertEquals("Test Book 2", bookDTO2.getTitle());
        assertEquals("Test Description 2", bookDTO2.getDescription());
        assertEquals(LocalDate.of(2021, 1, 1), bookDTO2.getPublicationDate());
    }

    @Test
    void testMapToEntityList() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Test Author");

        BookDTO bookDTO1 = new BookDTO.Builder()
                .id(1)
                .title("Test Book 1")
                .description("Test Description 1")
                .author(authorDTO)
                .publicationDate(LocalDate.of(2020, 1, 1))
                .build();

        BookDTO bookDTO2 = new BookDTO.Builder()
                .id(2)
                .title("Test Book 2")
                .description("Test Description 2")
                .author(authorDTO)
                .publicationDate(LocalDate.of(2021, 1, 1))
                .build();

        List<BookDTO> bookDTOs = Arrays.asList(bookDTO1, bookDTO2);
        List<Book> books = bookMapper.mapToEntity(bookDTOs);

        assertNotNull(books);
        assertEquals(2, books.size());

        Book book1 = books.getFirst();
        assertEquals(1, book1.getId());
        assertEquals("Test Book 1", book1.getTitle());
        assertEquals("Test Description 1", book1.getDescription());
        assertEquals(LocalDate.of(2020, 1, 1), book1.getPublicationDate());

        Book book2 = books.get(1);
        assertEquals(2, book2.getId());
        assertEquals("Test Book 2", book2.getTitle());
        assertEquals("Test Description 2", book2.getDescription());
        assertEquals(LocalDate.of(2021, 1, 1), book2.getPublicationDate());
    }
}