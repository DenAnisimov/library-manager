package org.example.dao;

import org.example.config.DataBaseConnection;
import org.example.entity.Book;
import org.example.entity.BookGenre;
import org.example.entity.Genre;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class BookGenreDAOImpl implements BookGenreDAO {

    private final DataBaseConnection dataBaseConnection;

    public BookGenreDAOImpl(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @Override
    public BookGenre getByBookId(int bookId) throws SQLException {
        String sql = "SELECT b.id AS book_id, b.title AS book_title, g.id AS genre_id, g.name AS genre_name " +
                "FROM book b " +
                "LEFT JOIN book_genre bg ON b.id = bg.book_id " +
                "LEFT JOIN genre g ON bg.genre_id = g.id " +
                "WHERE b.id = ?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                Book book = null;
                Set<Genre> genres = new HashSet<>();

                while (resultSet.next()) {
                    if (book == null) {
                        book = new Book.Builder()
                                .id(resultSet.getInt("book_id"))
                                .title(resultSet.getString("book_title"))
                                .build();
                    }

                    int genreId = resultSet.getInt("genre_id");
                    if (genreId > 0) {
                        String genreName = resultSet.getString("genre_name");
                        Genre genre = new Genre.Builder()
                                .id(genreId)
                                .name(genreName)
                                .build();
                        genres.add(genre);
                    }
                }

                if (book != null) {
                    BookGenre bookGenre = new BookGenre.Builder().build();
                    bookGenre.setBooks(Set.of(book));
                    bookGenre.setGenres(genres);
                    return bookGenre;
                } else {
                    return null;  // Книга не найдена
                }
            }
        }
    }

    @Override
    public BookGenre getByGenreId(int genreId) throws SQLException {
        String sql = "SELECT g.id AS genre_id, g.name AS genre_name, b.id AS book_id, b.title AS book_title " +
                "FROM genre g " +
                "LEFT JOIN book_genre bg ON g.id = bg.genre_id " +
                "LEFT JOIN book b ON bg.book_id = b.id " +
                "WHERE g.id = ?";

        try (Connection connection = dataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, genreId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                Genre genre = null;
                Set<Book> books = new HashSet<>();

                while (resultSet.next()) {
                    if (genre == null) {
                        genre = new Genre.Builder()
                                .id(resultSet.getInt("genre_id"))
                                .name(resultSet.getString("genre_name"))
                                .build();
                    }

                    int bookId = resultSet.getInt("book_id");
                    if (bookId > 0) {
                        String bookTitle = resultSet.getString("book_title");
                        Book book = new Book.Builder()
                                .id(bookId)
                                .title(bookTitle)
                                .build();
                        books.add(book);
                    }
                }

                if (genre != null) {
                    BookGenre bookGenre = new BookGenre.Builder().build();
                    bookGenre.setGenres(Set.of(genre));
                    bookGenre.setBooks(books);
                    return bookGenre;
                } else {
                    return null;
                }
            }
        }
    }
}
