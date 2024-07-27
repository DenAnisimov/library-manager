package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDetailsDTOTest {

    @Test
    void testAuthorDetailsDTODefaultConstructor() {
        AuthorDetailsDTO details = new AuthorDetailsDTO();
        assertEquals(0, details.getId());
        assertNull(details.getLifeYears());
        assertNull(details.getBriefBiography());
        assertNull(details.getAuthorDTO());
    }

    @Test
    void testAuthorDetailsDTOConstructorWithParameters() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO details = new AuthorDetailsDTO(1, "01/01/1830-01/01/1870", "Brief biography", author);
        assertEquals(1, details.getId());
        assertEquals("01/01/1830-01/01/1870", details.getLifeYears());
        assertEquals("Brief biography", details.getBriefBiography());
        assertEquals(author, details.getAuthorDTO());
    }

    @Test
    void testAuthorDetailsDTOBuilder() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO details = new AuthorDetailsDTO.Builder()
                .id(1)
                .lifeYears("01/01/1830-01/01/1870")
                .briefBiography("Brief biography")
                .authorDTO(author)
                .build();
        assertEquals(1, details.getId());
        assertEquals("01/01/1830-01/01/1870", details.getLifeYears());
        assertEquals("Brief biography", details.getBriefBiography());
        assertEquals(author, details.getAuthorDTO());
    }

    @Test
    void testAuthorDetailsDTOEqualsAndHashCode() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO details1 = new AuthorDetailsDTO(1, "01/01/1830-01/01/1870",
                "Brief biography", author);
        AuthorDetailsDTO details2 = new AuthorDetailsDTO(1, "01/01/1830-01/01/1870",
                "Brief biography", author);
        AuthorDetailsDTO details3 = new AuthorDetailsDTO(2, "01/01/1830-01/01/1890",
                "Different brief biography", author);

        assertEquals(details1, details2);
        assertNotEquals(details1, details3);
        assertEquals(details1.hashCode(), details2.hashCode());
    }

    @Test
    void testAuthorDetailsDTOToString() {
        AuthorDTO author = new AuthorDTO();
        AuthorDetailsDTO details = new AuthorDetailsDTO(1, "01/01/1830-01/01/1870",
                "Brief biography", author);
        assertEquals("AuthorDetailsDTO{id=1, lifeYears='01/01/1830-01/01/1870', " +
                "briefBiography='Brief biography', author=null}", details.toString());
    }
}
