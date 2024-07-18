package org.example.dao;

import org.example.config.DataBaseConnection;
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
        String sql = "SELECT a.id AS author_id, a.name AS author_name, " +
                "ad.id AS author_details_id, ad.phone_number AS author_details_phone_number, " +
                "ad.email AS author_details_email, " +
                "b.id AS book_id, b.title AS book_title, b.description AS book_description, " +
                "b.publication_date AS book_publication_date " +
                "FROM author a " +
                "LEFT JOIN author_details ad ON a.id = ad.author_id " +
                "LEFT JOIN book b ON a.id = b.author_id";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, Author> authorMap = new HashMap<>();

            while (resultSet.next()) {
                int authorId = resultSet.getInt("author_id");

                Author author = authorMap.get(authorId);
                if (author == null) {
                    author = buildAuthorFromResultSet(resultSet);
                    authorMap.put(authorId, author);
                }

            }

            return new ArrayList<>(authorMap.values());
        }
    }

    @Override
    public Author getById(int id) throws SQLException {
        String sql = "SELECT id, name FROM author WHERE id = ?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return buildAuthorFromResultSet(resultSet);
            } else {
                return null;
            }
        }
    }

    @Override
    public void insert(Author entity) throws SQLException {
        String sql = "INSERT INTO author (name) VALUES (?)";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Author entity) throws SQLException {
        String sql = "UPDATE author SET name=? WHERE id=?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM author WHERE id=?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Author buildAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        int authorId = resultSet.getInt("author_id");
        String authorName = resultSet.getString("author_name");

        AuthorDetails authorDetails = null;
        int authorDetailsId = resultSet.getInt("author_details_id");
        if (authorDetailsId > 0) {
            String phoneNumber = resultSet.getString("author_details_phone_number");
            String email = resultSet.getString("author_details_email");
            authorDetails = new AuthorDetails.Builder()
                    .id(authorDetailsId)
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .build();
        }

        Author author = new Author.Builder()
                .id(authorId)
                .name(authorName)
                .authorDetails(authorDetails)
                .build();

        Map<Integer, Book> bookMap = new HashMap<>();
        int bookId = resultSet.getInt("book_id");
        if (bookId > 0 && !bookMap.containsKey(bookId)) {
            String bookTitle = resultSet.getString("book_title");
            String bookDescription = resultSet.getString("book_description");
            java.sql.Date publicationDate = resultSet.getDate("book_publication_date");

            Book book = new Book.Builder()
                    .id(bookId)
                    .title(bookTitle)
                    .description(bookDescription)
                    .publicationDate(publicationDate.toLocalDate())
                    .build();
            bookMap.put(bookId, book);
            author.addBook(book);
        }

        return author;
    }
}
