package org.example.dto;

import java.time.LocalDate;
import java.util.Objects;

public class BookDTO {
    private int id;
    private String title;
    private String description;
    private AuthorDTO author;
    private LocalDate publicationDate;

    public BookDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDTO bookDTO)) return false;
        return id == bookDTO.id && Objects.equals(title, bookDTO.title) && Objects.equals(publicationDate, bookDTO.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publicationDate);
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + (author != null ? author.getName() : "null") +
                ", publicationDate=" + publicationDate +
                '}';
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private AuthorDTO author;
        private LocalDate publicationDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder author(AuthorDTO author) {
            this.author = author;
            return this;
        }

        public Builder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public BookDTO build() {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(this.id);
            bookDTO.setTitle(this.title);
            bookDTO.setDescription(this.description);
            bookDTO.setAuthor(this.author);
            bookDTO.setPublicationDate(this.publicationDate);
            return bookDTO;
        }
    }
}
