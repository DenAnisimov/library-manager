package org.example.dto;

import java.time.LocalDate;
import java.util.Objects;

public class BookDTO {
    private int id;
    private String title;
    private String description;
    private int authorId;
    private LocalDate publicationDate;

    public BookDTO() {}

    public BookDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.authorId = builder.authorId;
        this.publicationDate = builder.publicationDate;
    }

    public BookDTO(int id, String title, String description, int authorId, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.publicationDate = publicationDate;
    }

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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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
        return id == bookDTO.id && authorId == bookDTO.authorId
                && Objects.equals(title, bookDTO.title) && Objects.equals(publicationDate, bookDTO.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorId, publicationDate);
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private int authorId;
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

        public Builder authorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public BookDTO build() {
            return new BookDTO(this);
        }
    }
}
