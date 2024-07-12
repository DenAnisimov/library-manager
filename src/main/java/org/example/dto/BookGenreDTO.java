package org.example.dto;

public class BookGenreDTO {
    private int bookId;
    private int genreId;

    public BookGenreDTO() {}

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
}
