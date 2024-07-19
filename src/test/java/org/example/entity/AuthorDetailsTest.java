package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDetailsTest {

    @Test
    void testAuthorDetailsDefaultConstructor() {
        AuthorDetails details = new AuthorDetails();
        assertEquals(0, details.getId());
        assertNull(details.getPhoneNumber());
        assertNull(details.getEmail());
        assertNull(details.getAuthor());
    }

    @Test
    void testAuthorDetailsConstructorWithParameters() {
        Author author = new Author();
        AuthorDetails details = new AuthorDetails(1, "1234567890", "email@example.com", author);
        assertEquals(1, details.getId());
        assertEquals("1234567890", details.getPhoneNumber());
        assertEquals("email@example.com", details.getEmail());
        assertEquals(author, details.getAuthor());
    }

    @Test
    void testAuthorDetailsBuilder() {
        Author author = new Author();
        AuthorDetails details = new AuthorDetails.Builder()
                .id(1)
                .phoneNumber("1234567890")
                .email("email@example.com")
                .author(author)
                .build();
        assertEquals(1, details.getId());
        assertEquals("1234567890", details.getPhoneNumber());
        assertEquals("email@example.com", details.getEmail());
        assertEquals(author, details.getAuthor());
    }

    @Test
    void testAuthorDetailsEqualsAndHashCode() {
        Author author = new Author();
        AuthorDetails details1 = new AuthorDetails(1, "1234567890", "email@example.com", author);
        AuthorDetails details2 = new AuthorDetails(1, "1234567890", "email@example.com", author);
        AuthorDetails details3 = new AuthorDetails(2, "0987654321", "different@example.com", author);

        assertEquals(details1, details2);
        assertNotEquals(details1, details3);
        assertEquals(details1.hashCode(), details2.hashCode());
    }

    @Test
    void testAuthorDetailsToString() {
        Author author = new Author();
        AuthorDetails details = new AuthorDetails(1, "1234567890", "email@example.com", author);
        assertEquals("AuthorDetails{id=1, phoneNumber='1234567890', email='email@example.com', author=null}", details.toString());
    }
}
