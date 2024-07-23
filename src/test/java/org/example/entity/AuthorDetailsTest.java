package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDetailsTest {

    @Test
    void testAuthorDetailsDefaultConstructor() {
        AuthorDetails details = new AuthorDetails();
        assertEquals(0, details.getId());
        assertNull(details.getLifeYears());
        assertNull(details.getBriefBiography());
        assertNull(details.getAuthor());
    }

    @Test
    void testAuthorDetailsConstructorWithParameters() {
        Author author = new Author();
        AuthorDetails details = new AuthorDetails(1, "01/01/1830-01/01/1870", "Brief biography", author);
        assertEquals(1, details.getId());
        assertEquals("01/01/1830-01/01/1870", details.getLifeYears());
        assertEquals("Brief biography", details.getBriefBiography());
        assertEquals(author, details.getAuthor());
    }

    @Test
    void testAuthorDetailsBuilder() {
        Author author = new Author();
        AuthorDetails details = new AuthorDetails.Builder()
                .id(1)
                .lifeYears("01/01/1830-01/01/1870")
                .briefBiography("Brief biography")
                .author(author)
                .build();
        assertEquals(1, details.getId());
        assertEquals("01/01/1830-01/01/1870", details.getLifeYears());
        assertEquals("Brief biography", details.getBriefBiography());
        assertEquals(author, details.getAuthor());
    }

    @Test
    void testAuthorDetailsEqualsAndHashCode() {
        Author author = new Author();
        AuthorDetails details1 = new AuthorDetails(1, "01/01/1830-01/01/1870",
                "Brief biography", author);
        AuthorDetails details2 = new AuthorDetails(1, "01/01/1830-01/01/1870",
                "Brief biography", author);
        AuthorDetails details3 = new AuthorDetails(2, "01/01/1830-01/01/1890",
                "Different brief biography", author);

        assertEquals(details1, details2);
        assertNotEquals(details1, details3);
        assertEquals(details1.hashCode(), details2.hashCode());
    }

    @Test
    void testAuthorDetailsToString() {
        Author author = new Author();
        AuthorDetails details = new AuthorDetails(1, "01/01/1830-01/01/1870",
                "Brief biography", author);
        assertEquals("AuthorDetails{id=1, lifeYears='01/01/1830-01/01/1870', " +
                "briefBiography='Brief biography', author=null}", details.toString());
    }
}
