package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.dao.query.BookSqlQuery;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.BookGenre;
import org.example.entity.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDAOImpl implements BookDAO {

    private final DataBaseConnection dataBaseConnection;

    public BookDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        String sql = BookSqlQuery.GET_ALL.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            Map<Integer, Book> bookMap = new HashMap<>();

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");

                Book book = bookMap.get(bookId);
                if (book == null) {
                    book = buildBookFromResultSet(resultSet);
                    bookMap.put(bookId, book);
                }

                int authorId = resultSet.getInt("author_id");
                if (authorId > 0) {
                    String authorName = resultSet.getString("author_name");
                    Author author = new Author.Builder().id(authorId).name(authorName).build();
                    book.setAuthor(author);
                }
            }

            return new ArrayList<>(bookMap.values());
        }
    }

    @Override
    public List<Book> getAllByAuthor(int authorId) throws SQLException {
        String sql = BookSqlQuery.GET_ALL_BY_AUTHOR.getQuery();

        List<Book> books = new ArrayList<>();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = buildBookFromResultSet(resultSet);
                books.add(book);
            }
        }

        return books;
    }

    @Override
    public Book getById(int id) throws SQLException {
        String sql = BookSqlQuery.GET_BY_ID.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = buildBookFromResultSet(resultSet);
                    int authorId = resultSet.getInt("author_id");
                    if (authorId > 0) {
                        String authorName = resultSet.getString("author_name");
                        Author author = new Author.Builder().id(authorId).name(authorName).build();
                        book.setAuthor(author);
                    }
                    return book;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void insert(Book entity) throws SQLException {
        String sql = BookSqlQuery.INSERT.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getAuthor() != null ? entity.getAuthor().getId() : 0);
            preparedStatement.setDate(4, Date.valueOf(entity.getPublicationDate()));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Book entity) throws SQLException {
        String sql = BookSqlQuery.UPDATE.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getAuthor() != null ? entity.getAuthor().getId() : 0);
            preparedStatement.setDate(4, Date.valueOf(entity.getPublicationDate()));
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = BookSqlQuery.DELETE.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Book buildBookFromResultSet(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("book_id");
        String title = resultSet.getString("book_title");
        String description = resultSet.getString("book_description");
        Date publicationDate = resultSet.getDate("book_publication_date");

        return new Book.Builder()
                .id(bookId)
                .title(title)
                .description(description)
                .publicationDate(publicationDate.toLocalDate())
                .build();
    }
}
