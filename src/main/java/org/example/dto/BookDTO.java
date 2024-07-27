package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private int id;
    private String title;
    private String description;
    private AuthorDTO authorDTO;
    private LocalDate publicationDate;

    public BookDTO() {}

    public BookDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.publicationDate = builder.publicationDate;
        this.authorDTO = builder.authorDTO;
    }

    public BookDTO(int id, String title, String description, AuthorDTO authorDTO, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.authorDTO = authorDTO;
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

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
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
        return id == bookDTO.id && Objects.equals(title, bookDTO.title) && Objects.equals(publicationDate, bookDTO.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publicationDate);
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + (authorDTO != null ? authorDTO.getName() : "null") +
                ", publicationDate=" + publicationDate +
                '}';
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private AuthorDTO authorDTO;
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

        public Builder author(AuthorDTO authorDTO) {
            this.authorDTO = authorDTO;
            return this;
        }

        public Builder publicationDate(LocalDate publicationDate) {
            this.publicationDate = publicationDate;
            return this;
        }

        public BookDTO build() {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(this.id);
            bookDTO.setTitle(this.title);
            bookDTO.setDescription(this.description);
            bookDTO.setAuthorDTO(this.authorDTO);
            bookDTO.setPublicationDate(this.publicationDate);
            return bookDTO;
        }
    }
}
