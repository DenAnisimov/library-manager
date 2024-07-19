package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDetailsDTOTest {

    @Test
    void testDefaultConstructor() {
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO();
        assertNotNull(detailsDTO);
    }

    @Test
    void testParameterizedConstructor() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO(1, "1234567890", "email@example.com", author);

        assertEquals(1, detailsDTO.getId());
        assertEquals("1234567890", detailsDTO.getPhoneNumber());
        assertEquals("email@example.com", detailsDTO.getEmail());
        assertEquals(author, detailsDTO.getAuthor());
    }

    @Test
    void testGettersAndSetters() {
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO();
        AuthorDTO author = new AuthorDTO();

        detailsDTO.setId(1);
        detailsDTO.setPhoneNumber("1234567890");
        detailsDTO.setEmail("email@example.com");
        detailsDTO.setAuthor(author);

        assertEquals(1, detailsDTO.getId());
        assertEquals("1234567890", detailsDTO.getPhoneNumber());
        assertEquals("email@example.com", detailsDTO.getEmail());
        assertEquals(author, detailsDTO.getAuthor());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO detailsDTO1 = new AuthorDetailsDTO(1, "1234567890",
                "email@example.com", author);
        AuthorDetailsDTO detailsDTO2 = new AuthorDetailsDTO(1, "1234567890",
                "email@example.com", author);

        assertEquals(detailsDTO1, detailsDTO2);
        assertEquals(detailsDTO1.hashCode(), detailsDTO2.hashCode());
    }

    @Test
    void testToString() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO(1, "1234567890",
                "email@example.com", author);

        assertTrue(detailsDTO.toString().startsWith("AuthorDetailsDTO{id=1, phoneNumber='1234567890', " +
                "email='email@example.com', author="));
    }

    @Test
    void testBuilder() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO detailsDTO = new AuthorDetailsDTO.Builder()
                .id(1)
                .phoneNumber("1234567890")
                .email("email@example.com")
                .author(author)
                .build();

        assertEquals(1, detailsDTO.getId());
        assertEquals("1234567890", detailsDTO.getPhoneNumber());
        assertEquals("email@example.com", detailsDTO.getEmail());
        assertEquals(author, detailsDTO.getAuthor());
    }
}
