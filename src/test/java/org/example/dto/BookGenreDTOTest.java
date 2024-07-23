package org.example.dto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;

 class BookGenreDTOTest {

    @Test
     void testDefaultConstructor() {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        assertNotNull(bookGenreDTO);
    }

    @Test
     void testParameterizedConstructor() {
        Set<BookDTO> books = new HashSet<>();
        Set<GenreDTO> genres = new HashSet<>();
        BookGenreDTO bookGenreDTO = new BookGenreDTO(books, genres);

        assertEquals(books, bookGenreDTO.getBooks());
        assertEquals(genres, bookGenreDTO.getGenres());
    }

    @Test
     void testGettersAndSetters() {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        Set<BookDTO> books = new HashSet<>();
        Set<GenreDTO> genres = new HashSet<>();

        bookGenreDTO.setBooks(books);
        bookGenreDTO.setGenres(genres);

        assertEquals(books, bookGenreDTO.getBooks());
        assertEquals(genres, bookGenreDTO.getGenres());
    }

    @Test
     void testEqualsAndHashCode() {
        Set<BookDTO> books = new HashSet<>();
        Set<GenreDTO> genres = new HashSet<>();
        BookGenreDTO bookGenreDTO1 = new BookGenreDTO(books, genres);
        BookGenreDTO bookGenreDTO2 = new BookGenreDTO(books, genres);

        assertEquals(bookGenreDTO1, bookGenreDTO2);
        assertEquals(bookGenreDTO1.hashCode(), bookGenreDTO2.hashCode());
    }

    @Test
     void testToString() {
        Set<BookDTO> books = new HashSet<>();
        Set<GenreDTO> genres = new HashSet<>();
        BookGenreDTO bookGenreDTO = new BookGenreDTO(books, genres);

        assertTrue(bookGenreDTO.toString().startsWith("BookGenreDTO{books="));
    }

    @Test
     void testBuilder() {
        Set<BookDTO> books = new HashSet<>();
        Set<GenreDTO> genres = new HashSet<>();
        BookGenreDTO bookGenreDTO = new BookGenreDTO.Builder()
                .books(books)
                .genres(genres)
                .build();

        assertEquals(books, bookGenreDTO.getBooks());
        assertEquals(genres, bookGenreDTO.getGenres());
    }
}
