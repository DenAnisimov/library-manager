package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void testGenreDefaultConstructor() {
        Genre genre = new Genre();
        assertEquals(0, genre.getId());
        assertNull(genre.getName());
    }

    @Test
    void testGenreConstructorWithParameters() {
        Genre genre = new Genre(1, "Genre Name");
        assertEquals(1, genre.getId());
        assertEquals("Genre Name", genre.getName());
    }

    @Test
    void testGenreBuilder() {
        Genre genre = new Genre.Builder().id(1).name("Genre Name").build();
        assertEquals(1, genre.getId());
        assertEquals("Genre Name", genre.getName());
    }

    @Test
    void testGenreEqualsAndHashCode() {
        Genre genre1 = new Genre(1, "Genre Name");
        Genre genre2 = new Genre(1, "Genre Name");
        Genre genre3 = new Genre(2, "Different Name");

        assertEquals(genre1, genre2);
        assertNotEquals(genre1, genre3);
        assertEquals(genre1.hashCode(), genre2.hashCode());
    }

    @Test
    void testGenreToString() {
        Genre genre = new Genre(1, "Genre Name");
        assertEquals("Genre{id=1, name='Genre Name'}", genre.toString());
    }
}
