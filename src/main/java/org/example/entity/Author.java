package org.example.entity;

import java.util.Objects;

public class Author {
    private int id;
    private String name;
    private AuthorDetails authorDetails;

    public Author() {
    }

    public Author(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.authorDetails = builder.authorDetails;
    }

    public Author(int id, String name, AuthorDetails authorDetails) {
        this.id = id;
        this.name = name;
        this.authorDetails = authorDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuthorDetails getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(AuthorDetails authorDetails) {
        this.authorDetails = authorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return id == author.id && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorDetails=" + authorDetails +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private AuthorDetails authorDetails;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder authorDetails(AuthorDetails authorDetails) {
            this.authorDetails = authorDetails;
            return this;
        }

        public Author build() {
            return new Author(this);
        }
    }
}
