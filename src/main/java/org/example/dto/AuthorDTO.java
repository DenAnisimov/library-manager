package org.example.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorDTO {
    private int id;
    private String name;
    private AuthorDetailsDTO authorDetails;
    private List<BookDTO> books;

    public AuthorDTO() {
        this.books = new ArrayList<>();
    }

    public AuthorDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.authorDetails = builder.authorDetails;
        this.books = builder.books;
    }

    public AuthorDTO(int id, String name, AuthorDetailsDTO authorDetails, List<BookDTO> books) {
        this.id = id;
        this.name = name;
        this.authorDetails = authorDetails;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorDetailsDTO getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(AuthorDetailsDTO authorDetails) {
        this.authorDetails = authorDetails;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public void addBook(BookDTO book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDTO authorDTO)) return false;
        return id == authorDTO.id && Objects.equals(name, authorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorDetails=" + authorDetails +
                ", books=" + books +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private AuthorDetailsDTO authorDetails;
        private List<BookDTO> books;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder authorDetails(AuthorDetailsDTO authorDetails) {
            this.authorDetails = authorDetails;
            return this;
        }

        public Builder books(List<BookDTO> books) {
            this.books = books;
            return this;
        }

        public AuthorDTO build() {
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(this.id);
            authorDTO.setName(this.name);
            authorDTO.setAuthorDetails(this.authorDetails);
            authorDTO.setBooks(this.books);
            return authorDTO;
        }
    }
}
