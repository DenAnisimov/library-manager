package org.example.dto;

import java.time.LocalDate;

public class BookDTO {
    private int id;
    private String title;
    private String description;
    private int authorId;
    private LocalDate publicationDate;

    public BookDTO() {}

    public BookDTO(int id, String title, String description, int authorId, LocalDate publicationDate) {
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
}
