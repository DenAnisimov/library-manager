package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void testAuthorDefaultConstructor() {
        Author author = new Author();
        assertEquals(0, author.getId());
        assertNull(author.getName());
        assertTrue(author.getBooks().isEmpty());
        assertNull(author.getAuthorDetails());
    }

    @Test
    void testAuthorConstructorWithParameters() {
        AuthorDetails details = new AuthorDetails.Builder().id(1).build();
        Author author = new Author(1, "Author Name", details);
        assertEquals(1, author.getId());
        assertEquals("Author Name", author.getName());
        assertEquals(details, author.getAuthorDetails());
    }

    @Test
    void testAuthorBuilder() {
        AuthorDetails details = new AuthorDetails.Builder().id(1).build();
        Author author = new Author.Builder().id(1).name("Author Name").authorDetails(details).build();
        assertEquals(1, author.getId());
        assertEquals("Author Name", author.getName());
        assertEquals(details, author.getAuthorDetails());
    }

    @Test
    void testAuthorAddBook() {
        Author author = new Author();
        Book book = new Book.Builder().id(1).title("Book Title").build();
        author.addBook(book);
        assertTrue(author.getBooks().contains(book));
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testAuthorEqualsAndHashCode() {
        Author author1 = new Author(1, "Author Name", null);
        Author author2 = new Author(1, "Author Name", null);
        Author author3 = new Author(2, "Different Name", null);

        assertEquals(author1, author2);
        assertNotEquals(author1, author3);
        assertEquals(author1.hashCode(), author2.hashCode());
    }

    @Test
    void testAuthorToString() {
        Author author = new Author(1, "Author Name", null);
        String expectedString = "Author{id=1, name='Author Name', authorDetails=null, books=[]}";

        assertEquals(expectedString, author.toString());
    }

}
