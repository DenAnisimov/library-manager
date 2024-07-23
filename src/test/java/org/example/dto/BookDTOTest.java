package org.example.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;

class BookDTOTest {

    @Test
    void testDefaultConstructor() {
        BookDTO bookDTO = new BookDTO();
        assertNotNull(bookDTO);
    }

    @Test
    void testParameterizedConstructor() {
        AuthorDTO author = new AuthorDTO();
        LocalDate ationDate = LocalDate.of(2022, 1, 1);
        BookDTO bookDTO = new BookDTO(1, "Book Title", "Book Description", author, ationDate);

        assertEquals(1, bookDTO.getId());
        assertEquals("Book Title", bookDTO.getTitle());
        assertEquals("Book Description", bookDTO.getDescription());
        assertEquals(author, bookDTO.getAuthor());
        assertEquals(ationDate, bookDTO.getPublicationDate());
    }

    @Test
    void testGettersAndSetters() {
        BookDTO bookDTO = new BookDTO();
        AuthorDTO author = new AuthorDTO();
        LocalDate ationDate = LocalDate.of(2022, 1, 1);

        bookDTO.setId(1);
        bookDTO.setTitle("Book Title");
        bookDTO.setDescription("Book Description");
        bookDTO.setAuthor(author);
        bookDTO.setPublicationDate(ationDate);

        assertEquals(1, bookDTO.getId());
        assertEquals("Book Title", bookDTO.getTitle());
        assertEquals("Book Description", bookDTO.getDescription());
        assertEquals(author, bookDTO.getAuthor());
        assertEquals(ationDate, bookDTO.getPublicationDate());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthorDTO author = new AuthorDTO();
        LocalDate ationDate = LocalDate.of(2022, 1, 1);
        BookDTO bookDTO1 = new BookDTO(1, "Book Title", "Book Description", author, ationDate);
        BookDTO bookDTO2 = new BookDTO(1, "Book Title", "Book Description", author, ationDate);

        assertEquals(bookDTO1, bookDTO2);
        assertEquals(bookDTO1.hashCode(), bookDTO2.hashCode());
    }

    @Test
    void testToString() {
        AuthorDTO author = new AuthorDTO();
        LocalDate ationDate = LocalDate.of(2022, 1, 1);
        BookDTO bookDTO = new BookDTO(1, "Book Title",
                "Book Description", author, ationDate);

        assertTrue(bookDTO.toString().startsWith("BookDTO{id=1, title='Book Title', " +
                "description='Book Description', author="));
    }

    @Test
    void testBuilder() {
        AuthorDTO author = new AuthorDTO();
        LocalDate ationDate = LocalDate.of(2022, 1, 1);
        BookDTO bookDTO = new BookDTO.Builder()
                .id(1)
                .title("Book Title")
                .description("Book Description")
                .author(author)
                .publicationDate(ationDate)
                .build();

        assertEquals(1, bookDTO.getId());
        assertEquals("Book Title", bookDTO.getTitle());
        assertEquals("Book Description", bookDTO.getDescription());
        assertEquals(author, bookDTO.getAuthor());
        assertEquals(ationDate, bookDTO.getPublicationDate());
    }
}
