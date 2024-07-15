package org.example.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private String description;
    private int authorId;
    private LocalDate publicationDate;

    public Book() {}

    public Book(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.authorId = builder.authorId;
        this.publicationDate = builder.publicationDate;
    }

    public Book(int id, String title, String description, int authorId, LocalDate publicationDate) {

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
        if (!(o instanceof Book book)) return false;
        return id == book.id && authorId == book.authorId && Objects.equals(title, book.title) && Objects.equals(publicationDate, book.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorId, publicationDate);
    }

    public static class Builder {
        private String title;
        private String description;
        private int authorId;
        private LocalDate publicationDate;

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

        public Book build() {
            return new Book(this);
        }
    }
}
