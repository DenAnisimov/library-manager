package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookGenreDTO {
    private Set<BookDTO> bookDTOS;
    private Set<GenreDTO> genreDTOS;

    public BookGenreDTO() {}

    public BookGenreDTO(Builder builder) {
        this.bookDTOS = builder.bookDTOS;
        this.genreDTOS = builder.genresDTOS;
    }

    public BookGenreDTO(Set<BookDTO> bookDTOS, Set<GenreDTO> genreDTOS) {
        this.bookDTOS = bookDTOS;
        this.genreDTOS = genreDTOS;
    }

    public Set<BookDTO> getBookDTOS() {
        return bookDTOS;
    }

    public void setBookDTOS(Set<BookDTO> bookDTOS) {
        this.bookDTOS = bookDTOS;
    }

    public Set<GenreDTO> getGenreDTOS() {
        return genreDTOS;
    }

    public void setGenreDTOS(Set<GenreDTO> genreDTOS) {
        this.genreDTOS = genreDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenreDTO bookGenreDTO)) return false;
        return Objects.equals(bookDTOS, bookGenreDTO.bookDTOS) && Objects.equals(genreDTOS, bookGenreDTO.genreDTOS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookDTOS, genreDTOS);
    }

    @Override
    public String toString() {
        return "BookGenreDTO{" +
                "books=" + bookDTOS +
                ", genres=" + genreDTOS +
                '}';
    }

    public static class Builder {
        private Set<BookDTO> bookDTOS;
        private Set<GenreDTO> genresDTOS;

        public Builder books(Set<BookDTO> bookDTOS) {
            this.bookDTOS = bookDTOS;
            return this;
        }

        public Builder genres(Set<GenreDTO> genresDTOS) {
            this.genresDTOS = genresDTOS;
            return this;
        }

        public BookGenreDTO build() {
            BookGenreDTO bookGenreDTO = new BookGenreDTO();
            bookGenreDTO.setBookDTOS(this.bookDTOS);
            bookGenreDTO.setGenreDTOS(this.genresDTOS);
            return bookGenreDTO;
        }
    }
}
