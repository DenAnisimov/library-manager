package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDetailsDTO {
    private int id;
    private String lifeYears;
    private String briefBiography;
    @JsonIgnore
    private AuthorDTO authorDTO;

    public AuthorDetailsDTO() {}

    public AuthorDetailsDTO(Builder builder) {
        this.id = builder.id;
        this.lifeYears = builder.lifeYears;
        this.briefBiography = builder.briefBiography;
        this.authorDTO = builder.authorDTO;
    }

    public AuthorDetailsDTO(int id, String lifeYears, String briefBiography, AuthorDTO authorDTO) {
        this.id = id;
        this.lifeYears = lifeYears;
        this.briefBiography = briefBiography;
        this.authorDTO = authorDTO;
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

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDetailsDTO that)) return false;
        return id == that.id
                && Objects.equals(lifeYears, that.lifeYears)
                && Objects.equals(briefBiography, that.briefBiography)
                && Objects.equals(authorDTO, that.authorDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lifeYears, briefBiography, authorDTO);
    }

    @Override
    public String toString() {
        return "AuthorDetailsDTO{" +
                "id=" + id +
                ", lifeYears='" + lifeYears + '\'' +
                ", briefBiography='" + briefBiography + '\'' +
                ", author=" + (authorDTO != null ? authorDTO.getName() : "null") +
                '}';
    }

    public static class Builder {
        private int id;
        private String lifeYears;
        private String briefBiography;
        private AuthorDTO authorDTO;

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

        public Builder authorDTO(AuthorDTO authorDTO) {
            this.authorDTO = authorDTO;
            return this;
        }

        public AuthorDetailsDTO build() {
            return new AuthorDetailsDTO(this);
        }
    }
}
