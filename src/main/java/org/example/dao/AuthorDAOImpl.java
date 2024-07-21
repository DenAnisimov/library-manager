package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.dao.query.AuthorSqlQuery;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
import org.example.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDAOImpl implements AuthorDAO {

    private final DataBaseConnection dataBaseConnection;

    public AuthorDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public List<Author> getAll() throws SQLException {
        String sql = AuthorSqlQuery.GET_ALL.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            Map<Integer, Author> authorMap = new HashMap<>();

            while (resultSet.next()) {
                int authorId = resultSet.getInt("author_id");

                Author author = authorMap.get(authorId);
                if (author == null) {
                    author = buildAuthorFromResultSet(resultSet);
                    authorMap.put(authorId, author);
                } else {
                    addBookToAuthor(resultSet, author);
                }
            }

            return new ArrayList<>(authorMap.values());
        }
    }

    @Override
    public Author getById(int id) throws SQLException {
        String sql = AuthorSqlQuery.GET_BY_ID.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Author author = null;

                while (resultSet.next()) {
                    if (author == null) {
                        author = buildAuthorFromResultSet(resultSet);
                    } else {
                        addBookToAuthor(resultSet, author);
                    }
                }

                return author;
            }
        }
    }


    @Override
    public void insert(Author entity) throws SQLException {
        String sql = AuthorSqlQuery.INSERT.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Author entity) throws SQLException {
        String sql = AuthorSqlQuery.UPDATE.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = AuthorSqlQuery.DELETE.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private void addBookToAuthor(ResultSet resultSet, Author author) throws SQLException {
        int bookId = resultSet.getInt("book_id");
        if (bookId > 0) {
            String bookTitle = resultSet.getString("book_title");
            String bookDescription = resultSet.getString("book_description");
            java.sql.Date publicationDate = resultSet.getDate("book_publication_date");

            Book book = new Book.Builder()
                    .id(bookId)
                    .title(bookTitle)
                    .description(bookDescription)
                    .publicationDate(publicationDate != null ? publicationDate.toLocalDate() : null)
                    .build();

            author.addBook(book);
        }
    }

    private Author buildAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        int authorId = resultSet.getInt("author_id");
        String authorName = resultSet.getString("author_name");

        AuthorDetails authorDetails = null;
        int authorDetailsId = resultSet.getInt("author_details_id");
        if (authorDetailsId > 0) {
            String lifeYears = resultSet.getString("author_details_life_years");
            String briefBiography = resultSet.getString("author_details_brief_biography");
            authorDetails = new AuthorDetails.Builder()
                    .id(authorDetailsId)
                    .lifeYears(lifeYears)
                    .briefBiography(briefBiography)
                    .build();
        }

        Author author = new Author.Builder()
                .id(authorId)
                .name(authorName)
                .authorDetails(authorDetails)
                .build();

        addBookToAuthor(resultSet, author);

        return author;
    }
}
