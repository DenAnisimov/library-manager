package org.example.dto;

import java.util.List;
import java.util.Objects;

public class AuthorDTO {
    private int id;
    private String name;
    private AuthorDetailsDTO authorDetails;
    private List<BookDTO> books;

    public AuthorDTO() {}

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
