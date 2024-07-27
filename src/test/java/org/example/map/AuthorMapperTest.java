package org.example.map;

import org.example.dto.AuthorDTO;
import org.example.dto.AuthorDetailsDTO;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthorMapperTest {
    private static AuthorMapper authorMapper;

    @BeforeAll
    static void setUp() {
        authorMapper = Mappers.getMapper(AuthorMapper.class);
    }
    @Test
    void testMapToEntity() {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setBriefBiography("Some bio");

        AuthorDTO authorDTO = new AuthorDTO(1, "Author Name", authorDetailsDTO);

        Author author = authorMapper.mapToEntity(authorDTO);

        assertNotNull(author);
        assertEquals(1, author.getId());
        assertEquals("Author Name", author.getName());
        assertNotNull(author.getAuthorDetails());
        assertEquals("Some bio", author.getAuthorDetails().getBriefBiography());
    }

    @Test
    void testMapToDTO() {
        AuthorDetails authorDetails = new AuthorDetails();
        authorDetails.setBriefBiography("Some bio");

        Author author = new Author(1, "Author Name", authorDetails);

        AuthorDTO authorDTO = authorMapper.mapToDTO(author);

        assertNotNull(authorDTO);
        assertEquals(1, authorDTO.getId());
        assertEquals("Author Name", authorDTO.getName());
        assertNotNull(authorDTO.getAuthorDetails());
        assertEquals("Some bio", authorDTO.getAuthorDetails().getBriefBiography());
    }

    @Test
    void testMapToEntityList() {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setBriefBiography("Some bio");

        AuthorDTO authorDTO = new AuthorDTO(1, "Author Name", authorDetailsDTO);

        List<AuthorDTO> authorDTOList = List.of(authorDTO);

        List<Author> authorList = authorMapper.mapToEntity(authorDTOList);

        assertNotNull(authorList);
        assertEquals(1, authorList.size());
        assertEquals(1, authorList.get(0).getId());
        assertEquals("Author Name", authorList.get(0).getName());
        assertNotNull(authorList.get(0).getAuthorDetails());
        assertEquals("Some bio", authorList.get(0).getAuthorDetails().getBriefBiography());
    }

    @Test
    void testMapToDTOList() {
        AuthorDetails authorDetails = new AuthorDetails();
        authorDetails.setBriefBiography("Some bio");

        Author author = new Author(1, "Author Name", authorDetails);

        List<Author> authorList = List.of(author);

        List<AuthorDTO> authorDTOList = authorMapper.mapToDTO(authorList);

        assertNotNull(authorDTOList);
        assertEquals(1, authorDTOList.size());
        assertEquals(1, authorDTOList.get(0).getId());
        assertEquals("Author Name", authorDTOList.get(0).getName());
        assertNotNull(authorDTOList.get(0).getAuthorDetails());
        assertEquals("Some bio", authorDTOList.get(0).getAuthorDetails().getBriefBiography());
    }
}
