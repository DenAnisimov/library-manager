package org.example.entity;

import java.util.Objects;

public class AuthorDetails {
    private int id;
    private String phoneNumber;
    private String email;
    private Author author;

    public AuthorDetails() {}

    public AuthorDetails(Builder builder) {
        this.id = builder.id;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.author = builder.author;
    }

    public AuthorDetails(int id, String phoneNumber, String email, Author author) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDetails that)) return false;
        return id == that.id
                && Objects.equals(phoneNumber, that.phoneNumber)
                && Objects.equals(email, that.email)
                && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, email, author);
    }

    @Override
    public String toString() {
        return "AuthorDetails{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", author=" + (author != null ? author.getName() : "null") +
                '}';
    }

    public static class Builder {
        private int id;
        private String phoneNumber;
        private String email;
        private Author author;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder author(Author author) {
            this.author = author;
            return this;
        }

        public AuthorDetails build() {
            return new AuthorDetails(this);
        }
    }
}