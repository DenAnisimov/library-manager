package org.example.map;

import org.example.dto.GenreDTO;
import org.example.entity.Genre;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreMapperTest {
    private final GenreMapper genreMapper = Mappers.getMapper(GenreMapper.class);

    @Test
    void testMapToDTO() {
        Genre genre = new Genre.Builder()
                .id(1)
                .name("Science Fiction")
                .build();

        GenreDTO genreDTO = genreMapper.mapToDTO(genre);

        assertNotNull(genreDTO);
        assertEquals(1, genreDTO.getId());
        assertEquals("Science Fiction", genreDTO.getName());
    }

    @Test
    void testMapToEntity() {
        GenreDTO genreDTO = new GenreDTO.Builder()
                .id(1)
                .name("Science Fiction")
                .build();

        Genre genre = genreMapper.mapToEntity(genreDTO);

        assertNotNull(genre);
        assertEquals(1, genre.getId());
        assertEquals("Science Fiction", genre.getName());
    }

    @Test
    void testMapToDTOList() {
        Genre genre1 = new Genre.Builder()
                .id(1)
                .name("Science Fiction")
                .build();

        Genre genre2 = new Genre.Builder()
                .id(2)
                .name("Fantasy")
                .build();

        List<Genre> genres = Arrays.asList(genre1, genre2);
        List<GenreDTO> genreDTOs = genreMapper.mapToDTO(genres);

        assertNotNull(genreDTOs);
        assertEquals(2, genreDTOs.size());

        GenreDTO genreDTO1 = genreDTOs.getFirst();
        assertEquals(1, genreDTO1.getId());
        assertEquals("Science Fiction", genreDTO1.getName());

        GenreDTO genreDTO2 = genreDTOs.get(1);
        assertEquals(2, genreDTO2.getId());
        assertEquals("Fantasy", genreDTO2.getName());
    }

    @Test
    void testMapToEntityList() {
        GenreDTO genreDTO1 = new GenreDTO.Builder()
                .id(1)
                .name("Science Fiction")
                .build();

        GenreDTO genreDTO2 = new GenreDTO.Builder()
                .id(2)
                .name("Fantasy")
                .build();

        List<GenreDTO> genreDTOs = Arrays.asList(genreDTO1, genreDTO2);
        List<Genre> genres = genreMapper.mapToEntity(genreDTOs);

        assertNotNull(genres);
        assertEquals(2, genres.size());

        Genre genre1 = genres.getFirst();
        assertEquals(1, genre1.getId());
        assertEquals("Science Fiction", genre1.getName());

        Genre genre2 = genres.get(1);
        assertEquals(2, genre2.getId());
        assertEquals("Fantasy", genre2.getName());
    }
}
