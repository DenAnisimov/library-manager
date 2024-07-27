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
        AuthorDTO authorDTO = new AuthorDTO(1, "Author Name", detailsDTO);

        assertEquals(1, authorDTO.getId());
        assertEquals("Author Name", authorDTO.getName());
        assertEquals(detailsDTO, authorDTO.getAuthorDetails());
    }

    @Test
    void testGettersAndSetters() {
        AuthorDTO authorDTO = new AuthorDTO();
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO();
        List<BookDTO> books = new ArrayList<>();

        authorDTO.setId(1);
        authorDTO.setName("Author Name");
        authorDTO.setAuthorDetails(detailsDTO);
        assertEquals(1, authorDTO.getId());
        assertEquals("Author Name", authorDTO.getName());
        assertEquals(detailsDTO, authorDTO.getAuthorDetails());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthorDTO authorDTO1 = new AuthorDTO(1, "Author Name", null);
        AuthorDTO authorDTO2 = new AuthorDTO(1, "Author Name", null);

        assertEquals(authorDTO1, authorDTO2);
        assertEquals(authorDTO1.hashCode(), authorDTO2.hashCode());
    }

    @Test
    void testToString() {
        AuthorDTO authorDTO = new AuthorDTO(1, "Author Name", null);
        String expected = "AuthorDTO{id=1, name='Author Name', authorDetails=null}";

        assertEquals(expected, authorDTO.toString());
    }

    @Test
    void testBuilder() {
        AuthorDTO authorDTO = new AuthorDTO.Builder()
                .id(1)
                .name("Author Name")
                .authorDetails(null)
                .build();

        assertEquals(1, authorDTO.getId());
        assertEquals("Author Name", authorDTO.getName());
        assertNull(authorDTO.getAuthorDetails());
    }
}


