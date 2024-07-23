package org.example.dto;

import java.util.Objects;
import java.util.Set;

public class BookGenreDTO {
    private Set<BookDTO> books;
    private Set<GenreDTO> genres;

    public BookGenreDTO() {}

    public BookGenreDTO(Builder builder) {
        this.books = builder.books;
        this.genres = builder.genres;
    }

    public BookGenreDTO(Set<BookDTO> books, Set<GenreDTO> genres) {
        this.books = books;
        this.genres = genres;
    }

    public Set<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDTO> books) {
        this.books = books;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenreDTO bookGenreDTO)) return false;
        return Objects.equals(books, bookGenreDTO.books) && Objects.equals(genres, bookGenreDTO.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books, genres);
    }

    @Override
    public String toString() {
        return "BookGenreDTO{" +
                "books=" + books +
                ", genres=" + genres +
                '}';
    }

    public static class Builder {
        private Set<BookDTO> books;
        private Set<GenreDTO> genres;

        public Builder books(Set<BookDTO> books) {
            this.books = books;
            return this;
        }

        public Builder genres(Set<GenreDTO> genres) {
            this.genres = genres;
            return this;
        }

        public BookGenreDTO build() {
            BookGenreDTO bookGenreDTO = new BookGenreDTO();
            bookGenreDTO.setBooks(this.books);
            bookGenreDTO.setGenres(this.genres);
            return bookGenreDTO;
        }
    }
}
