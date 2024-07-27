package org.example.map;

import org.example.dto.BookDTO;
import org.example.dto.BookGenreDTO;
import org.example.dto.GenreDTO;
import org.example.entity.Book;
import org.example.entity.BookGenre;
import org.example.entity.Genre;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookGenreMapperTest {
    private static BookGenreMapper bookGenreMapper;

    @BeforeAll
    static void setUp() {
        bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);
    }

    @Test
    void testMapToDTO() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");

        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Test Genre");

        Set<Book> books = new HashSet<>();
        books.add(book);

        Set<Genre> genres = new HashSet<>();
        genres.add(genre);

        BookGenre bookGenre = new BookGenre.Builder()
                .books(books)
                .genres(genres)
                .build();

        BookGenreDTO bookGenreDTO = bookGenreMapper.mapToDTO(bookGenre);

        assertNotNull(bookGenreDTO);
        assertNotNull(bookGenreDTO.getBookDTOS());
        assertNotNull(bookGenreDTO.getGenreDTOS());
        assertEquals(1, bookGenreDTO.getBookDTOS().size());
        assertEquals(1, bookGenreDTO.getGenreDTOS().size());

        BookDTO bookDTO = bookGenreDTO.getBookDTOS().iterator().next();
        assertEquals(1, bookDTO.getId());
        assertEquals("Test Book", bookDTO.getTitle());

        GenreDTO genreDTO = bookGenreDTO.getGenreDTOS().iterator().next();
        assertEquals(1, genreDTO.getId());
        assertEquals("Test Genre", genreDTO.getName());
    }

    @Test
    void testMapToEntity() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1);
        bookDTO.setTitle("Test Book");

        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(1);
        genreDTO.setName("Test Genre");

        Set<BookDTO> bookDTOS = new HashSet<>();
        bookDTOS.add(bookDTO);

        Set<GenreDTO> genreDTOS = new HashSet<>();
        genreDTOS.add(genreDTO);

        BookGenreDTO bookGenreDTO = new BookGenreDTO.Builder()
                .books(bookDTOS)
                .genres(genreDTOS)
                .build();

        BookGenre bookGenre = bookGenreMapper.mapToEntity(bookGenreDTO);

        assertNotNull(bookGenre);
        assertNotNull(bookGenre.getBooks());
        assertNotNull(bookGenre.getGenres());
        assertEquals(1, bookGenre.getBooks().size());
        assertEquals(1, bookGenre.getGenres().size());

        Book book = bookGenre.getBooks().iterator().next();
        assertEquals(1, book.getId());
        assertEquals("Test Book", book.getTitle());

        Genre genre = bookGenre.getGenres().iterator().next();
        assertEquals(1, genre.getId());
        assertEquals("Test Genre", genre.getName());
    }
}
