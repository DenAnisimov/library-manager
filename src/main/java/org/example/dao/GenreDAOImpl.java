package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO {
    @Override
    public List<Genre> getAll() throws SQLException {
        String sql = "select id, name from genre";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Genre> genres = new ArrayList<>();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("id"));
                genre.setName(resultSet.getString("name"));
                genres.add(genre);
            }
            return genres;
        }
    }

    @Override
    public Genre getById(int id) throws SQLException {
        String sql = "select id, name from genre where id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("id"));
                genre.setName(resultSet.getString("name"));
                return genre;
            } else {
                return null;
            }
        }
    }

    @Override
    public void insert(Genre entity) throws SQLException {
        String sql = "insert into genre (name) values (?)";
        try (Connection connection = new DataBaseConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Genre entity) throws SQLException {
        String sql = "update genre set name = ? where id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from genre where id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
