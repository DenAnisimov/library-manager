package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.BookGenre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookGenreDAOImpl implements BookGenreDAO {
    @Override
    public List<BookGenre> getAll() throws SQLException {
        String sql = "select book_id, genre_id from book_genre";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BookGenre> bookGenres = new ArrayList<>();
            while (resultSet.next()) {
                BookGenre bookGenre = new BookGenre.Builder()
                        .bookId(resultSet.getInt("book_id"))
                        .genreId(resultSet.getInt("genre_id"))
                        .build();
                bookGenres.add(bookGenre);
            }
            return bookGenres;
        }
    }

    @Override
    public BookGenre getById(int id) throws SQLException {
        String sql = "select book_id, genre_id from book_genre where book_id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new BookGenre.Builder()
                        .bookId(resultSet.getInt("book_id"))
                        .genreId(resultSet.getInt("genre_id"))
                        .build();
            } else {
                return null;
            }
        }
    }

    @Override
    public void insert(BookGenre entity) throws SQLException {
        String sql = "insert into book_genre(genre_id) values(?)";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getGenreId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(BookGenre entity) throws SQLException {
        String sql = "update book_genre set genre_id = ? where book_id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, entity.getGenreId());
            preparedStatement.setInt(2, entity.getBookId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from book_genre where book_id = ?";
        try (Connection connection = new DataBaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
