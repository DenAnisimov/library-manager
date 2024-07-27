package org.example.map;

import org.example.dto.AuthorDTO;
import org.example.dto.AuthorDetailsDTO;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDetailsMapperTest {
    private static AuthorDetailsMapper authorDetailsMapper;

    @BeforeAll
    static void setUp() {
        authorDetailsMapper = Mappers.getMapper(AuthorDetailsMapper.class);
    }

    @Test
    void testMapToEntity() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Author Name");

        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO(1, "1900-1980", "Brief biography", authorDTO);

        AuthorDetails authorDetails = authorDetailsMapper.mapToEntity(authorDetailsDTO);

        assertNotNull(authorDetails);
        assertEquals(1, authorDetails.getId());
        assertEquals("1900-1980", authorDetails.getLifeYears());
        assertEquals("Brief biography", authorDetails.getBriefBiography());
        assertNotNull(authorDetails.getAuthor());
        assertEquals(1, authorDetails.getAuthor().getId());
        assertEquals("Author Name", authorDetails.getAuthor().getName());
    }

    @Test
    void testMapToDTO() {
        Author author = new Author();
        author.setId(1);
        author.setName("Author Name");

        AuthorDetails authorDetails = new AuthorDetails(1, "1900-1980", "Brief biography", author);

        AuthorDetailsDTO authorDetailsDTO = authorDetailsMapper.mapToDTO(authorDetails);

        assertNotNull(authorDetailsDTO);
        assertEquals(1, authorDetailsDTO.getId());
        assertEquals("1900-1980", authorDetailsDTO.getLifeYears());
        assertEquals("Brief biography", authorDetailsDTO.getBriefBiography());
        assertNotNull(authorDetailsDTO.getAuthorDTO());
        assertEquals(1, authorDetailsDTO.getAuthorDTO().getId());
        assertEquals("Author Name", authorDetailsDTO.getAuthorDTO().getName());
    }

    @Test
    void testMapToEntityList() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Author Name");

        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO(1, "1900-1980", "Brief biography", authorDTO);
        List<AuthorDetailsDTO> authorDetailsDTOList = List.of(authorDetailsDTO);

        List<AuthorDetails> authorDetailsList = authorDetailsMapper.mapToEntity(authorDetailsDTOList);

        assertNotNull(authorDetailsList);
        assertEquals(1, authorDetailsList.size());
        assertEquals(1, authorDetailsList.getFirst().getId());
        assertEquals("1900-1980", authorDetailsList.getFirst().getLifeYears());
        assertEquals("Brief biography", authorDetailsList.getFirst().getBriefBiography());
        assertNotNull(authorDetailsList.getFirst().getAuthor());
        assertEquals(1, authorDetailsList.getFirst().getAuthor().getId());
        assertEquals("Author Name", authorDetailsList.getFirst().getAuthor().getName());
    }

    @Test
    void testMapToDTOList() {
        Author author = new Author();
        author.setId(1);
        author.setName("Author Name");

        AuthorDetails authorDetails = new AuthorDetails(1, "1900-1980", "Brief biography", author);
        List<AuthorDetails> authorDetailsList = List.of(authorDetails);

        List<AuthorDetailsDTO> authorDetailsDTOList = authorDetailsMapper.mapToDTO(authorDetailsList);

        assertNotNull(authorDetailsDTOList);
        assertEquals(1, authorDetailsDTOList.size());
        assertEquals(1, authorDetailsDTOList.getFirst().getId());
        assertEquals("1900-1980", authorDetailsDTOList.getFirst().getLifeYears());
        assertEquals("Brief biography", authorDetailsDTOList.getFirst().getBriefBiography());
        assertNotNull(authorDetailsDTOList.getFirst().getAuthorDTO());
        assertEquals(1, authorDetailsDTOList.getFirst().getAuthorDTO().getId());
        assertEquals("Author Name", authorDetailsDTOList.getFirst().getAuthorDTO().getName());
    }
}