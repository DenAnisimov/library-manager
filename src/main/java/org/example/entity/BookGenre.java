package org.example.entity;

import java.util.Objects;

public class BookGenre {
    private int bookId;
    private int genreId;

    public BookGenre() {}

    public BookGenre(Builder builder) {
        this.bookId = builder.bookId;
        this.genreId = builder.genreId;
    }

    public BookGenre(int bookId, int genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenre bookGenre)) return false;
        return bookId == bookGenre.bookId && genreId == bookGenre.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, genreId);
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "bookId=" + bookId +
                ", genreId=" + genreId +
                '}';
    }

    public static class Builder {
        private int bookId;
        private int genreId;

        public Builder bookId(int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder genreId(int genreId) {
            this.genreId = genreId;
            return this;
        }

        public BookGenre build() {
            return new BookGenre(this);
        }
    }
}
