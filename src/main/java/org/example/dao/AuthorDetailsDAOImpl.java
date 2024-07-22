package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.dao.query.AuthorDetailsSqlQuery;
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

    private final AuthorDAO authorDAO;

    public AuthorDetailsDAOImpl(DataBaseConnection dataBaseConnection, AuthorDAO authorDAO) {
        this.dataBaseConnection = dataBaseConnection;
        this.authorDAO = authorDAO;
    }

    @Override
    public List<AuthorDetails> getAll() throws SQLException {
        String sql = AuthorDetailsSqlQuery.GET_ALL.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

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
        String sql = AuthorDetailsSqlQuery.GET_BY_ID.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return buildAuthorDetailsFromResultSet(resultSet);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public void insert(AuthorDetails entity) throws SQLException {
        String sql = AuthorDetailsSqlQuery.INSERT.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getLifeYears());
            preparedStatement.setString(2, entity.getBriefBiography());
            preparedStatement.setInt(3, entity.getAuthor().getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(AuthorDetails entity) throws SQLException {
        String sql = AuthorDetailsSqlQuery.UPDATE.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getLifeYears());
            preparedStatement.setString(2, entity.getBriefBiography());
            preparedStatement.setInt(3, entity.getAuthor().getId());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = AuthorDetailsSqlQuery.DELETE.getQuery();

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private AuthorDetails buildAuthorDetailsFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String lifeYears = resultSet.getString("life_years");
        String briefBiography = resultSet.getString("brief_biography");
        int authorId = resultSet.getInt("author_id");

        Author author = authorDAO.getById(authorId);

        return new AuthorDetails.Builder()
                .id(id)
                .lifeYears(lifeYears)
                .briefBiography(briefBiography)
                .author(author)
                .build();
    }
}