package org.example.dao.query;

public enum AuthorSqlQuery {

    GET_ALL(
            "SELECT " +
                    "a.id AS author_id, " +
                    "a.name AS author_name, " +
                    "ad.id AS author_details_id, " +
                    "ad.life_years AS author_details_life_years, " +
                    "ad.brief_biography AS author_details_brief_biography, " +
                    "b.id AS book_id, " +
                    "b.title AS book_title, " +
                    "b.description AS book_description, " +
                    "b.publication_date AS book_publication_date " +
                    "FROM author a " +
                    "LEFT JOIN author_details ad ON a.id = ad.author_id " +
                    "LEFT JOIN book b ON a.id = b.author_id " +
                    "ORDER BY a.id, b.id;"
    ),
    GET_BY_ID(
            "SELECT " +
                    "a.author_id, " +
                    "a.author_name, " +
                    "ad.author_details_id, " +
                    "ad.author_details_life_years, " +
                    "ad.author_details_brief_biography, " +
                    "b.book_id, " +
                    "b.book_title, " +
                    "b.book_description, " +
                    "b.book_publication_date " +
                    "FROM authors a " +
                    "LEFT JOIN author_details ad ON a.author_id = ad.author_id " +
                    "LEFT JOIN books b ON a.author_id = b.author_id " +
                    "WHERE a.author_id = ?;"
    ),
    INSERT(
            "INSERT INTO author (name) VALUES (?);"
    ),
    UPDATE(
            "UPDATE author SET name=? WHERE id=?;"
    ),
    DELETE(
            "DELETE FROM author WHERE id=?;"
    );

    private final String query;

    AuthorSqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
