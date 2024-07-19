package org.example.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDTOTest {

    @Test
    void testDefaultConstructor() {
        AuthorDTO authorDTO = new AuthorDTO();
        assertNotNull(authorDTO);
    }

    @Test
    void testParameterizedConstructor() {
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO();
        List<BookDTO> books = new ArrayList<>();
        AuthorDTO authorDTO = new AuthorDTO(1, "Author Name", detailsDTO, books);

        assertEquals(1, authorDTO.getId());
        assertEquals("Author Name", authorDTO.getName());
        assertEquals(detailsDTO, authorDTO.getAuthorDetails());
        assertEquals(books, authorDTO.getBooks());
    }

    @Test
    void testGettersAndSetters() {
        AuthorDTO authorDTO = new AuthorDTO();
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO();
        List<BookDTO> books = new ArrayList<>();

        authorDTO.setId(1);
        authorDTO.setName("Author Name");
        authorDTO.setAuthorDetails(detailsDTO);
        authorDTO.setBooks(books);

        assertEquals(1, authorDTO.getId());
        assertEquals("Author Name", authorDTO.getName());
        assertEquals(detailsDTO, authorDTO.getAuthorDetails());
        assertEquals(books, authorDTO.getBooks());
    }

    @Test
    void testAddBook() {
        AuthorDTO authorDTO = new AuthorDTO();
        BookDTO bookDTO = new BookDTO();
        authorDTO.addBook(bookDTO);

        assertTrue(authorDTO.getBooks().contains(bookDTO));
    }

    @Test
    void testEqualsAndHashCode() {
        AuthorDTO authorDTO1 = new AuthorDTO(1, "Author Name", null, new ArrayList<>());
        AuthorDTO authorDTO2 = new AuthorDTO(1, "Author Name", null, new ArrayList<>());

        assertEquals(authorDTO1, authorDTO2);
        assertEquals(authorDTO1.hashCode(), authorDTO2.hashCode());
    }

    @Test
    void testToString() {
        AuthorDTO authorDTO = new AuthorDTO(1, "Author Name", null, new ArrayList<>());
        String expected = "AuthorDTO{id=1, name='Author Name', authorDetails=null, books=[]}";

        assertEquals(expected, authorDTO.toString());
    }

    @Test
    void testBuilder() {
        AuthorDTO authorDTO = new AuthorDTO.Builder()
                .id(1)
                .name("Author Name")
                .authorDetails(null)
                .books(new ArrayList<>())
                .build();

        assertEquals(1, authorDTO.getId());
        assertEquals("Author Name", authorDTO.getName());
        assertEquals(null, authorDTO.getAuthorDetails());
        assertEquals(new ArrayList<>(), authorDTO.getBooks());
    }
}


