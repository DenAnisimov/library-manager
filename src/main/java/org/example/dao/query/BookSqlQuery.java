package org.example.dao.query;

public enum BookSqlQuery {

    GET_ALL(
            "SELECT id, title, description, " +
                    "publication_date, author_id FROM book"
    ),
    GET_ALL_BY_AUTHOR(
            "SELECT id, title, description, " +
                    "publication_date, author_id FROM book" +
                    "WHERE author_id = ?"
    ),
    GET_BY_ID(
            "SELECT id, title, description, publication_date, author_id FROM book " +
                    "WHERE id = ?"
    ),
    INSERT(
            "INSERT INTO book (title, description, author_id, publication_date) VALUES (?, ?, ?, ?)"
    ),
    UPDATE(
            "UPDATE book SET title = ?, description = ?, author_id = ?, publication_date = ? WHERE id = ?"
    ),
    DELETE(
            "DELETE FROM book WHERE id = ?"
    );

    private final String query;

    BookSqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
