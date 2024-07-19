package org.example.dao.query;

public enum BookSqlQuery {

    GET_ALL(
            "SELECT b.id AS book_id, b.title AS book_title, b.description AS book_description, " +
                    "b.publication_date AS book_publication_date, a.id AS author_id, a.name AS author_name " +
                    "FROM book b LEFT JOIN author a ON b.author_id = a.id"
    ),
    GET_ALL_BY_AUTHOR(
            "SELECT b.id AS book_id, b.title AS book_title, b.description AS book_description, " +
                    "b.publication_date AS book_publication_date, a.id AS author_id, a.name AS author_name " +
                    "FROM book b LEFT JOIN author a ON b.author_id = a.id WHERE a.id = ?"
    ),
    GET_BY_ID(
            "SELECT b.id AS book_id, b.title AS book_title, b.description AS book_description, " +
                    "b.publication_date AS book_publication_date, a.id AS author_id, a.name AS author_name " +
                    "FROM book b LEFT JOIN author a ON b.author_id = a.id WHERE b.id = ?"
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
