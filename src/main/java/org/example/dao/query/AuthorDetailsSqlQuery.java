package org.example.dao.query;

public enum AuthorDetailsSqlQuery {

    GET_ALL(
            "SELECT id, phone_number, email, author_id FROM author_details"
    ),
    GET_BY_ID(
            "SELECT id, phone_number, email, author_id FROM author_details WHERE id = ?"
    ),
    INSERT(
            "INSERT INTO author_details (phone_number, email, author_id) VALUES (?, ?, ?)"
    ),
    UPDATE(
            "UPDATE author_details SET phone_number=?, email=?, author_id=? WHERE id=?"
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
