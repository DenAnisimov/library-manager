package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDetailsDAOImpl implements AuthorDetailsDAO {

    private final DataBaseConnection dataBaseConnection;

    public AuthorDetailsDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public List<AuthorDetails> getAll() throws SQLException {
        String sql = "SELECT id, phone_number, email, author_id FROM author_details";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<AuthorDetails> authorDetailsList = new ArrayList<>();

            while (resultSet.next()) {
                AuthorDetails authorDetails = buildAuthorDetailsFromResultSet(resultSet);
                authorDetailsList.add(authorDetails);
            }

            return authorDetailsList;
        }
    }

    @Override
    public AuthorDetails getById(int id) throws SQLException {
        String sql = "SELECT id, phone_number, email, author_id FROM author_details WHERE id = ?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return buildAuthorDetailsFromResultSet(resultSet);
            } else {
                return null;
            }
        }
    }

    @Override
    public void insert(AuthorDetails entity) throws SQLException {
        String sql = "INSERT INTO author_details (phone_number, email, author_id) VALUES (?, ?, ?)";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getPhoneNumber());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setInt(3, entity.getAuthor().getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(AuthorDetails entity) throws SQLException {
        String sql = "UPDATE author_details SET phone_number=?, email=?, author_id=? WHERE id=?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getPhoneNumber());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setInt(3, entity.getAuthor().getId());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM author_details WHERE id=?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private AuthorDetails buildAuthorDetailsFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String phoneNumber = resultSet.getString("phone_number");
        String email = resultSet.getString("email");
        int authorId = resultSet.getInt("author_id");

        Author author = new Author.Builder().id(authorId).build();

        return new AuthorDetails.Builder()
                .id(id)
                .phoneNumber(phoneNumber)
                .email(email)
                .author(author)
                .build();
    }
}