package org.example.dao.query;

public enum AuthorDetailsSqlQuery {

    GET_ALL(
            "SELECT id, life_years, brief_biography, author_id FROM author_details"
    ),
    GET_BY_ID(
            "SELECT id, life_years, brief_biography, author_id FROM author_details WHERE id = ?"
    ),
    INSERT(
            "INSERT INTO author_details (life_years, brief_biography, author_id) VALUES (?, ?, ?)"
    ),
    UPDATE(
            "UPDATE author_details SET life_years=?, brief_biography=?, author_id=? WHERE id=?"
    ),
    DELETE(
            "DELETE FROM author_details WHERE id=?"
    );

    private final String query;

    AuthorDetailsSqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
