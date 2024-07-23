package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GenreDTOTest {

    @Test
    void testDefaultConstructor() {
        GenreDTO genreDTO = new GenreDTO();
        assertNotNull(genreDTO);
    }

    @Test
    void testParameterizedConstructor() {
        GenreDTO genreDTO = new GenreDTO(1, "Genre Name");

        assertEquals(1, genreDTO.getId());
        assertEquals("Genre Name", genreDTO.getName());
    }

    @Test
    void testGettersAndSetters() {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(1);
        genreDTO.setName("Genre Name");

        assertEquals(1, genreDTO.getId());
        assertEquals("Genre Name", genreDTO.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        GenreDTO genreDTO1 = new GenreDTO(1, "Genre Name");
        GenreDTO genreDTO2 = new GenreDTO(1, "Genre Name");

        assertEquals(genreDTO1, genreDTO2);
        assertEquals(genreDTO1.hashCode(), genreDTO2.hashCode());
    }

    @Test
    void testToString() {
        GenreDTO genreDTO = new GenreDTO(1, "Genre Name");
        String expected = "GenreDTO{id=1, name='Genre Name'}";

        assertEquals(expected, genreDTO.toString());
    }

    @Test
    void testBuilder() {
        GenreDTO genreDTO = new GenreDTO.Builder()
                .id(1)
                .name("Genre Name")
                .build();

        assertEquals(1, genreDTO.getId());
        assertEquals("Genre Name", genreDTO.getName());
    }
}
