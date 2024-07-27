package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.DataBaseConnection;
import org.example.dao.AuthorDAO;
import org.example.dao.AuthorDAOImpl;
import org.example.dao.BookDAO;
import org.example.dao.BookDAOImpl;
import org.example.dto.BookDTO;
import org.example.map.AuthorMapper;
import org.example.map.BookMapper;
import org.example.service.AuthorService;
import org.example.service.BookService;
import org.example.util.JsonResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.example.util.ServletUtil.isNullOrEmpty;
import static org.example.util.ServletUtil.sendError;

@WebServlet("/book/*")
public class BookServlet extends HttpServlet {
    private BookService bookService;
    private AuthorService authorService;
    private JsonResponse jsonResponse;

    @Override
    public void init() throws ServletException {
        jsonResponse = new JsonResponse();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        AuthorDAO authorDAO = new AuthorDAOImpl(dataBaseConnection);
        BookDAO bookDAO = new BookDAOImpl(dataBaseConnection, authorDAO);
        bookService = new BookService(bookDAO, BookMapper.INSTANCE);
        authorService = new AuthorService(authorDAO, AuthorMapper.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertBook(req, resp);
                    break;
                case "/update":
                    updateBook(req, resp);
                    break;
                case "/delete":
                    deleteBook(req, resp);
                    break;
                case "/list":
                    getAllBooks(resp);
                    break;
                case "/listByAuthor":
                    getAllBooksByAuthor(req, resp);
                    break;
                case "/getById":
                    getById(req, resp);
                    break;
                default:
                    sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Not Found");
                    break;
            }
        } catch (IOException e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            BookDTO book = bookService.findById(id);
            if (book == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Book not found");
            } else {
                jsonResponse.sendJsonResponse(resp, book);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void getAllBooks(HttpServletResponse resp) throws IOException {
        List<BookDTO> books = bookService.findAll();
        jsonResponse.sendJsonResponse(resp, books);
    }

    private void getAllBooksByAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authorIdParam = req.getParameter("author_id");
        if (isNullOrEmpty(authorIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'author_id' parameter");
            return;
        }

        try {
            int authorId = Integer.parseInt(authorIdParam);
            List<BookDTO> books = bookService.findAllByAuthor(authorId);
            jsonResponse.sendJsonResponse(resp, books);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            bookService.delete(id);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void updateBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        String authorIdParam = req.getParameter("author_id");
        String titleParam = req.getParameter("title");
        String descriptionParam = req.getParameter("description");
        String publicationDateParam = req.getParameter("publication_date");

        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        if (isNullOrEmpty(authorIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'author_id' parameter");
            return;
        }

        if (isNullOrEmpty(titleParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'title' parameter");
            return;
        }

        if (isNullOrEmpty(descriptionParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'description' parameter");
            return;
        }

        if (isNullOrEmpty(publicationDateParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'publication date' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            BookDTO book = bookService.findById(id);
            if (book == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Book not found");
            } else {
                book.setAuthorDTO(authorService.findById(Integer.parseInt(authorIdParam)));
                book.setTitle(titleParam);
                book.setDescription(descriptionParam);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                book.setPublicationDate(LocalDate.parse(publicationDateParam, formatter));

                bookService.update(book);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void insertBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authorIdParam = req.getParameter("author_id");
        String titleParam = req.getParameter("title");
        String descriptionParam = req.getParameter("description");
        String publicationDateParam = req.getParameter("publication_date");

        if (isNullOrEmpty(authorIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'author_id' parameter");
            return;
        }

        if (isNullOrEmpty(titleParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'title' parameter");
            return;
        }

        if (isNullOrEmpty(descriptionParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'description' parameter");
            return;
        }

        if (isNullOrEmpty(publicationDateParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'publication date' parameter");
            return;
        }

        try {
            BookDTO book = new BookDTO();
            book.setAuthorDTO(authorService.findById(Integer.parseInt(authorIdParam)));
            book.setTitle(titleParam);
            book.setDescription(descriptionParam);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            book.setPublicationDate(LocalDate.parse(publicationDateParam, formatter));

            bookService.add(book);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        } catch (DateTimeParseException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'publication date' format");
        }
    }
}
