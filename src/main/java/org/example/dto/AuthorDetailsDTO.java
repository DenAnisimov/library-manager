package org.example.dto;

import java.util.Objects;

public class AuthorDetailsDTO {
    private int id;
    private String lifeYears;
    private String briefBiography;
    private AuthorDTO author;

    public AuthorDetailsDTO() {}

    public AuthorDetailsDTO(Builder builder) {
        this.id = builder.id;
        this.lifeYears = builder.lifeYears;
        this.briefBiography = builder.briefBiography;
        this.author = builder.author;
    }

    public AuthorDetailsDTO(int id, String lifeYears, String briefBiography, AuthorDTO author) {
        this.id = id;
        this.lifeYears = lifeYears;
        this.briefBiography = briefBiography;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLifeYears() {
        return lifeYears;
    }

    public void setLifeYears(String lifeYears) {
        this.lifeYears = lifeYears;
    }

    public String getBriefBiography() {
        return briefBiography;
    }

    public void setBriefBiography(String briefBiography) {
        this.briefBiography = briefBiography;
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
        return id == that.id
                && Objects.equals(lifeYears, that.lifeYears)
                && Objects.equals(briefBiography, that.briefBiography)
                && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lifeYears, briefBiography, author);
    }

    @Override
    public String toString() {
        return "AuthorDetails{" +
                "id=" + id +
                ", lifeYears='" + lifeYears + '\'' +
                ", briefBiography='" + briefBiography + '\'' +
                ", author=" + (author != null ? author.getName() : "null") +
                '}';
    }

    public static class Builder {
        private int id;
        private String lifeYears;
        private String briefBiography;
        private AuthorDTO author;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder lifeYears(String lifeYears) {
            this.lifeYears = lifeYears;
            return this;
        }

        public Builder briefBiography(String briefBiography) {
            this.briefBiography = briefBiography;
            return this;
        }

        public Builder author(AuthorDTO author) {
            this.author = author;
            return this;
        }

        public AuthorDetailsDTO build() {
            return new AuthorDetailsDTO(this);
        }
    }
}
