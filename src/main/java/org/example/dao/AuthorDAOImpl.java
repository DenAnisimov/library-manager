package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public List<Author> getAll() throws SQLException {
        String sql = "SELECT id, name FROM author";

        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Author> authors = new ArrayList<>();

            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("id"));
                author.setName(resultSet.getString("name"));
                authors.add(author);
            }
            return authors;
        }
    }

    @Override
    public Author getById(int id) throws SQLException {
        String sql = "SELECT id, name FROM author WHERE id = ?";

        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("id"));
                author.setName(resultSet.getString("name"));
                return author;
            } else {
                return null;
            }
        }
    }

    @Override
    public void insert(Author entity) throws SQLException {
        String sql = "INSERT INTO author (name) VALUES(?)";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.execute();
        }
    }

    @Override
    public void update(Author entity) throws SQLException {
        String sql = "UPDATE author SET name=? WHERE id=?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM author WHERE id=?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }
    }
}
