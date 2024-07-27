package org.example.dao.query;

public enum BookGenreSqlQuery {

    GET_BY_BOOK_ID(
            "SELECT b.id AS book_id, b.title AS book_title, b.description as book_description, " +
                    "b.publication_date as book_publication_date, " +
                    "g.id AS genre_id, g.name AS genre_name " +
                    "FROM book b LEFT JOIN book_genre bg ON b.id = bg.book_id " +
                    "LEFT JOIN genre g ON bg.genre_id = g.id WHERE b.id = ?"
    ),
    GET_BY_GENRE_ID(
            "SELECT g.id AS genre_id, g.name AS genre_name, " +
                    "b.id AS book_id, b.title AS book_title, b.description as book_description, " +
                    "b.publication_date as book_publication_date " +
                    "FROM genre g LEFT JOIN book_genre bg ON g.id = bg.genre_id " +
                    "LEFT JOIN book b ON bg.book_id = b.id WHERE g.id = ?"
    ),
    GET_ALL(
            "SELECT g.id AS genre_id, g.name AS genre_name, " +
                    "b.id AS book_id, b.title AS book_title, b.description as book_description, " +
                    "b.publication_date as book_publication_date " +
                    "FROM genre g LEFT JOIN book_genre bg ON g.id = bg.genre_id " +
                    "LEFT JOIN book b ON bg.book_id = b.id"
    ),
    INSERT (
            "INSERT INTO book_genre VALUES (?, ?)"
    ),
    DELETE(
            "DELETE FROM book_genre " +
            "WHERE book_id = ? AND genre_id = ?"
    );

    private final String query;

    BookGenreSqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
