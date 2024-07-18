package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO {

    private final DataBaseConnection dataBaseConnection;

    public GenreDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public List<Genre> getAll() throws SQLException {
        String sql = "SELECT id, name FROM genre";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Genre> genres = new ArrayList<>();

            while (resultSet.next()) {
                Genre genre = new Genre.Builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                genres.add(genre);
            }

            return genres;
        }
    }

    @Override
    public Genre getById(int id) throws SQLException {
        String sql = "SELECT id, name FROM genre WHERE id = ?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Genre.Builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .build();
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void insert(Genre entity) throws SQLException {
        String sql = "INSERT INTO genre (name) VALUES (?)";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Genre entity) throws SQLException {
        String sql = "UPDATE genre SET name = ? WHERE id = ?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM genre WHERE id = ?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
