package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.DataBaseConnection;
import org.example.dao.BookGenreDAO;
import org.example.dao.BookGenreDAOImpl;
import org.example.dto.BookGenreDTO;
import org.example.map.BookGenreMapper;
import org.example.service.BookGenreService;
import org.example.util.JsonResponse;

import java.io.IOException;

import static org.example.util.ServletUtil.isNullOrEmpty;
import static org.example.util.ServletUtil.sendError;

@WebServlet("/book-genre/*")
public class BookGenreServlet extends HttpServlet {
    private BookGenreService bookGenreService;
    private JsonResponse jsonResponse;

    @Override
    public void init() throws ServletException {
        jsonResponse = new JsonResponse();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        BookGenreDAO bookGenreDAO = new BookGenreDAOImpl(dataBaseConnection);
        bookGenreService = new BookGenreService(bookGenreDAO, BookGenreMapper.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertBookGenre(req, resp);
                    break;
                case "/delete":
                    deleteBookGenre(req, resp);
                    break;
                case "/list":
                    getAll(resp);
                    break;
                case "/getByGenreId":
                    getAllByGenreId(req, resp);
                    break;
                case "/getByBookId":
                    getAllByBookId(req, resp);
                    break;
                default:
                    sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Not Found");
                    break;
            }
        } catch (IOException e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void getAll(HttpServletResponse resp) throws IOException {
        BookGenreDTO bookGenreDTO = bookGenreService.getAll();
        jsonResponse.sendJsonResponse(resp, bookGenreDTO);
    }

    private void getAllByGenreId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String genreIdParam = req.getParameter("genreId");

        if (isNullOrEmpty(genreIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'genre id' parameter");
            return;
        }
        try {
            int genreId = Integer.parseInt(genreIdParam);
            BookGenreDTO bookGenreDTO = bookGenreService.getAllByGenreId(genreId);
            jsonResponse.sendJsonResponse(resp, bookGenreDTO);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'genre id' parameter");
        }
    }

    private void getAllByBookId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bookIdParam = req.getParameter("bookId");

        if (isNullOrEmpty(bookIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'book id' parameter");
            return;
        }
        try {
            int bookId = Integer.parseInt(bookIdParam);
            BookGenreDTO bookGenreDTO = bookGenreService.getAllByBookId(bookId);
            jsonResponse.sendJsonResponse(resp, bookGenreDTO);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'book id' parameter");
        }
    }

    private void insertBookGenre(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bookIdParam = req.getParameter("bookId");
        String genreIdParam = req.getParameter("genreId");

        if (isNullOrEmpty(bookIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'book id' parameter");
            return;
        }

        if (isNullOrEmpty(genreIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'genre id' parameter");
            return;
        }


        try {
            int bookId = Integer.parseInt(bookIdParam);
            int genreId = Integer.parseInt(genreIdParam);
            bookGenreService.add(bookId, genreId);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        } catch (RuntimeException e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the author");
        }
    }

    private void deleteBookGenre(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bookIdParam = req.getParameter("bookId");
        String genreIdParam = req.getParameter("genreId");

        if (isNullOrEmpty(bookIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'book id' parameter");
            return;
        }

        if (isNullOrEmpty(genreIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'genre id' parameter");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdParam);
            int genreId = Integer.parseInt(genreIdParam);
            bookGenreService.delete(bookId, genreId);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        } catch (RuntimeException e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the author");
        }
    }
}
