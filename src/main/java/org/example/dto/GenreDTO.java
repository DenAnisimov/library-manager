package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDTO {
    private int id;
    private String name;

    public GenreDTO() {}

    public GenreDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public GenreDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreDTO genreDTO)) return false;
        return id == genreDTO.id && Objects.equals(name, genreDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public GenreDTO build() {
            GenreDTO genreDTO = new GenreDTO();
            genreDTO.setId(this.id);
            genreDTO.setName(this.name);
            return genreDTO;
        }
    }
}
