package org.example.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookDefaultConstructor() {
        Book book = new Book();
        assertEquals(0, book.getId());
        assertNull(book.getTitle());
        assertNull(book.getDescription());
        assertNull(book.getAuthor());
        assertNull(book.getPublicationDate());
    }

    @Test
    void testBookConstructorWithParameters() {
        Author author = new Author();
        Book book = new Book(1, "Book Title", "Book Description", author, LocalDate.of(2024, 7, 19));
        assertEquals(1, book.getId());
        assertEquals("Book Title", book.getTitle());
        assertEquals("Book Description", book.getDescription());
        assertEquals(author, book.getAuthor());
        assertEquals(LocalDate.of(2024, 7, 19), book.getPublicationDate());
    }

    @Test
    void testBookBuilder() {
        Author author = new Author();
        Book book = new Book.Builder()
                .id(1)
                .title("Book Title")
                .description("Book Description")
                .author(author)
                .publicationDate(LocalDate.of(2024, 7, 19))
                .build();
        assertEquals(1, book.getId());
        assertEquals("Book Title", book.getTitle());
        assertEquals("Book Description", book.getDescription());
        assertEquals(author, book.getAuthor());
        assertEquals(LocalDate.of(2024, 7, 19), book.getPublicationDate());
    }

    @Test
    void testBookEqualsAndHashCode() {
        Author author = new Author();
        Book book1 = new Book(1, "Book Title", "Book Description", author, LocalDate.of(2024, 7, 19));
        Book book2 = new Book(1, "Book Title", "Book Description", author, LocalDate.of(2024, 7, 19));
        Book book3 = new Book(2, "Different Title", "Different Description", author, LocalDate.of(2024, 8, 19));

        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void testBookToString() {
        Author author = new Author();
        Book book = new Book(1, "Book Title", "Book Description", author, LocalDate.of(2024, 7, 19));
        assertEquals("Book{id=1, title='Book Title', description='Book Description', author=null, publicationDate=2024-07-19}", book.toString());
    }
}
