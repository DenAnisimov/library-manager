package org.example.dao.query;

public enum AuthorSqlQuery {

    GET_ALL(
            "SELECT a.id AS author_id, a.name AS author_name, " +
                    "ad.id AS author_details_id, ad.phone_number AS author_details_phone_number, " +
                    "ad.email AS author_details_email, " +
                    "b.id AS book_id, b.title AS book_title, b.description AS book_description, " +
                    "b.publication_date AS book_publication_date " +
                    "FROM author a " +
                    "LEFT JOIN author_details ad ON a.id = ad.author_id " +
                    "LEFT JOIN book b ON a.id = b.author_id"
    ),
    GET_BY_ID(
            "SELECT id, name FROM author WHERE id = ?"
    ),
    INSERT(
            "INSERT INTO author (name) VALUES (?)"
    ),
    UPDATE(
            "UPDATE author SET name=? WHERE id=?"
    ),
    DELETE(
            "DELETE FROM author WHERE id=?"
    );

    private final String query;

    AuthorSqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
