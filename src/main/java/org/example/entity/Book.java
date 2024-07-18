package org.example.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private String description;
    private Author author;
    private LocalDate publicationDate;
    private Publisher publisher;

    public Book() {}

    public Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.author = builder.author;
        this.publicationDate = builder.publicationDate;
        this.publisher = builder.publisher;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id == book.id && Objects.equals(title, book.title) &&
                Objects.equals(publicationDate, book.publicationDate) &&
                Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publicationDate, publisher);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + (author != null ? author.getName() : "null") +
                ", publicationDate=" + publicationDate +
                ", publisher=" + (publisher != null ? publisher.getName() : "null") +
                '}';
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private Author author;
        private LocalDate publicationDate;
        private Publisher publisher;

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

        public Builder publisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
