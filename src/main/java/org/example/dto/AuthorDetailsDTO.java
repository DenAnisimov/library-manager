package org.example.dto;

import java.util.Objects;

public class AuthorDetailsDTO {
    private int id;
    private String phoneNumber;
    private String email;
    private AuthorDTO author;

    public AuthorDetailsDTO() {}

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

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDetailsDTO that)) return false;
        return id == that.id &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, email, author);
    }

    @Override
    public String toString() {
        return "AuthorDetailsDTO{" +
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
        private AuthorDTO author;

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

        public Builder author(AuthorDTO author) {
            this.author = author;
            return this;
        }

        public AuthorDetailsDTO build() {
            AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
            authorDetailsDTO.setId(this.id);
            authorDetailsDTO.setPhoneNumber(this.phoneNumber);
            authorDetailsDTO.setEmail(this.email);
            authorDetailsDTO.setAuthor(this.author);
            return authorDetailsDTO;
        }
    }
}
