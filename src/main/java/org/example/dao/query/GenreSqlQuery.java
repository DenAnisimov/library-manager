package org.example.dao.query;

public enum GenreSqlQuery {

    GET_ALL(
            "SELECT id, name FROM genre"
    ),
    GET_BY_ID(
            "SELECT id, name FROM genre WHERE id = ?"
    ),
    INSERT(
            "INSERT INTO genre (name) VALUES (?)"
    ),
    UPDATE(
            "UPDATE genre SET name = ? WHERE id = ?"
    ),
    DELETE(
            "DELETE FROM genre WHERE id = ?"
    );

    private final String query;

    GenreSqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
