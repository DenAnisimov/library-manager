package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public List<Author> getAll() throws Exception {
        try (Connection connection = new DataBaseConnection().getConnection()) {
            String sql = "select * from author";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
    public Author getById(int id) throws Exception {
        try (Connection connection = new DataBaseConnection().getConnection()) {
            String sql = MessageFormat.format("select * from author where id={0}", id);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
    public void insert(Author entity) throws Exception {
        try (Connection connection = new DataBaseConnection().getConnection()) {
            String sql = "INSERT INTO author(name) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.execute();
        }
    }

    @Override
    public void update(Author entity) throws Exception {
        try (Connection connection = new DataBaseConnection().getConnection()) {
            String sql = "UPDATE author SET name=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection connection = new DataBaseConnection().getConnection()) {
            String sql = "DELETE FROM author WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }
    }
}
