package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO {
    private int id;
    private String name;
    private AuthorDetailsDTO authorDetails;

    public AuthorDTO() {
    }

    public AuthorDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.authorDetails = builder.authorDetails;
    }

    public AuthorDTO(int id, String name, AuthorDetailsDTO authorDetails) {
        this.id = id;
        this.name = name;
        this.authorDetails = authorDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorDetailsDTO getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(AuthorDetailsDTO authorDetails) {
        this.authorDetails = authorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDTO authorDTO)) return false;
        return id == authorDTO.id && Objects.equals(name, authorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorDetails=" + authorDetails +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private AuthorDetailsDTO authorDetails;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder authorDetails(AuthorDetailsDTO authorDetails) {
            this.authorDetails = authorDetails;
            return this;
        }

        public AuthorDTO build() {
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(this.id);
            authorDTO.setName(this.name);
            authorDTO.setAuthorDetails(this.authorDetails);
            return authorDTO;
        }
    }
}
