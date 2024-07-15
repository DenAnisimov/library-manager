package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    @Override
    public List<Book> getAll() throws SQLException {
        String sql = "SELECT id, author_id, title, description, publication_date FROM book";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = new Book.Builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .description(resultSet.getString("description"))
                        .authorId(resultSet.getInt("author_id"))
                        .publicationDate(resultSet.getDate("publication_date").toLocalDate())
                        .build();
                books.add(book);
            }
            return books;
        }
    }

    @Override
    public Book getById(int id) throws SQLException {
        String sql = "SELECT id, author_id, title, description, publication_date FROM book WHERE id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Book.Builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .description(resultSet.getString("description"))
                        .authorId(resultSet.getInt("author_id"))
                        .publicationDate(resultSet.getDate("publication_date").toLocalDate())
                        .build();
            } else {
                return null;
            }
        }
    }

    @Override
    public void insert(Book entity) throws SQLException {
        String sql = "INSERT INTO book VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setInt(3, entity.getAuthorId());
            preparedStatement.setString(4, entity.getDescription());
            preparedStatement.setDate(5, Date.valueOf(entity.getPublicationDate()));
            preparedStatement.execute();
        }
    }

    @Override
    public void update(Book entity) throws SQLException {
        String sql = "UPDATE book SET title = ?, author_id = ?, description = ?, publication_date = ? WHERE id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setInt(2, entity.getAuthorId());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setDate(4, Date.valueOf(entity.getPublicationDate()));
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }
    }
}
