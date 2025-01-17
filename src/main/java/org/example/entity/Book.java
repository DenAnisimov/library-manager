package org.example.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private String description;
    private LocalDate publicationDate;
    private Author author;

    public Book() {}

    public Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.publicationDate = builder.publicationDate;
        this.author = builder.author;
    }

    public Book(int id, String title, String description, Author author, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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
        if (!(o instanceof Book book)) return false;
        return id == book.id && Objects.equals(title, book.title) &&
                Objects.equals(publicationDate, book.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publicationDate);
    }

    @Override
    public String toString() {
        return "Book{" +
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
        private Author author;
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

        public Builder author(Author author) {
            this.author = author;
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
