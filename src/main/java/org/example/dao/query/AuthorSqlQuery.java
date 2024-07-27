package org.example.dao.query;

public enum AuthorSqlQuery {

    GET_ALL(
            "SELECT " +
                    "a.id AS author_id, " +
                    "a.name AS author_name, " +
                    "ad.id AS author_details_id, " +
                    "ad.life_years AS author_details_life_years, " +
                    "ad.brief_biography AS author_details_brief_biography " +
                    "FROM author a " +
                    "LEFT JOIN author_details ad ON a.id = ad.author_id " +
                    "ORDER BY a.id;"
    ),
    GET_BY_ID(
            "SELECT " +
                    "a.id AS author_id, " +
                    "a.name AS author_name, " +
                    "ad.id AS author_details_id, " +
                    "ad.life_years AS author_details_life_years, " +
                    "ad.brief_biography AS author_details_brief_biography " +
                    "FROM author a " +
                    "LEFT JOIN author_details ad ON a.id = ad.author_id " +
                    "WHERE a.id = ?"
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
