package org.example.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookGenreTest {

    @Test
    void testBookGenreDefaultConstructor() {
        BookGenre bookGenre = new BookGenre();
        assertTrue(bookGenre.getBooks().isEmpty());
        assertTrue(bookGenre.getGenres().isEmpty());
    }

    @Test
    void testBookGenreBuilder() {
        Set<Book> books = new HashSet<>();
        Set<Genre> genres = new HashSet<>();
        BookGenre bookGenre = new BookGenre.Builder()
                .books(books)
                .genres(genres)
                .build();
        assertEquals(books, bookGenre.getBooks());
        assertEquals(genres, bookGenre.getGenres());
    }

    @Test
    void testBookGenreAddRemoveBook() {
        BookGenre bookGenre = new BookGenre();
        Book book = new Book.Builder().id(1).title("Book Title").build();
        bookGenre.addBook(book);
        assertTrue(bookGenre.getBooks().contains(book));
        bookGenre.removeBook(book);
        assertFalse(bookGenre.getBooks().contains(book));
    }

    @Test
    void testBookGenreAddRemoveGenre() {
        BookGenre bookGenre = new BookGenre();
        Genre genre = new Genre.Builder().id(1).name("Genre Name").build();
        bookGenre.addGenre(genre);
        assertTrue(bookGenre.getGenres().contains(genre));
        bookGenre.removeGenre(genre);
        assertFalse(bookGenre.getGenres().contains(genre));
    }

    @Test
    void testBookGenreEqualsAndHashCode() {
        Set<Book> books = new HashSet<>();
        Set<Genre> genres = new HashSet<>();
        Set<Genre> genres1 = new HashSet<>();
        genres1.add(new Genre.Builder().id(1).name("Genre Name").build());
        BookGenre bookGenre1 = new BookGenre.Builder().books(books).genres(genres).build();
        BookGenre bookGenre2 = new BookGenre.Builder().books(books).genres(genres).build();
        BookGenre bookGenre3 = new BookGenre.Builder().books(books).genres(genres1).build();

        assertEquals(bookGenre1, bookGenre2);
        assertNotEquals(bookGenre1, bookGenre3);
        assertEquals(bookGenre1.hashCode(), bookGenre2.hashCode());
    }

    @Test
    void testBookGenreToString() {
        BookGenre bookGenre = new BookGenre();
        assertEquals("BookGenre{books=[], genres=[]}", bookGenre.toString());
    }
}
