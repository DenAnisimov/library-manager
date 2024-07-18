package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Author {
    private int id;
    private String name;
    private AuthorDetails authorDetails;
    private List<Book> books;

    public Author() {
        this.books = new ArrayList<>();
    }

    public Author(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.books = new ArrayList<>();
        this.authorDetails = builder.authorDetails;
    }

    public Author(int id, String name, AuthorDetails authorDetails) {
        this.id = id;
        this.name = name;
        this.authorDetails = authorDetails;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    public AuthorDetails getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(AuthorDetails authorDetails) {
        this.authorDetails = authorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return id == author.id && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private AuthorDetails authorDetails;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder authorDetails(AuthorDetails authorDetails) {
            this.authorDetails = authorDetails;
            return this;
        }

        public Author build() {
            return new Author(this);
        }
    }
}
