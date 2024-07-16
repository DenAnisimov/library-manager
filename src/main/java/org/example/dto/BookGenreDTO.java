package org.example.dto;

import java.util.Objects;

public class BookGenreDTO {
    private int bookId;
    private int genreId;

    public BookGenreDTO() {}

    public BookGenreDTO(Builder builder) {
        this.bookId = builder.bookId;
        this.genreId = builder.genreId;
    }

    public BookGenreDTO(int bookId, int genreId) {
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
        if (!(o instanceof BookGenreDTO bookGenreDTO)) return false;
        return bookId == bookGenreDTO.bookId && genreId == bookGenreDTO.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, genreId);
    }

    @Override
    public String toString() {
        return "BookGenreDTO{" +
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

        public BookGenreDTO build() {
            return new BookGenreDTO(this);
        }
    }
}
