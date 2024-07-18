package org.example.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BookGenre {
    private Set<Book> books = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();

    public BookGenre() {}

    public BookGenre(Builder builder) {
        this.books = builder.books;
        this.genres = builder.genres;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenre bookGenre)) return false;
        return Objects.equals(books, bookGenre.books) && Objects.equals(genres, bookGenre.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books, genres);
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "books=" + books +
                ", genres=" + genres +
                '}';
    }

    public static class Builder {
        private Set<Book> books = new HashSet<>();
        private Set<Genre> genres = new HashSet<>();

        public Builder books(Set<Book> books) {
            this.books = books;
            return this;
        }

        public Builder genres(Set<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public BookGenre build() {
            return new BookGenre(this);
        }
    }
}
