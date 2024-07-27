package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.DataBaseConnection;
import org.example.dao.AuthorDAO;
import org.example.dao.AuthorDAOImpl;
import org.example.dto.AuthorDTO;
import org.example.map.AuthorMapper;
import org.example.service.AuthorService;
import org.example.util.JsonResponse;

import java.io.IOException;
import java.util.List;

import static org.example.util.ServletUtil.isNullOrEmpty;
import static org.example.util.ServletUtil.sendError;

@WebServlet("/author/*")
public class AuthorServlet extends HttpServlet {
    private AuthorService authorService;
    private JsonResponse jsonResponse;

    @Override
    public void init() throws ServletException {
        jsonResponse = new JsonResponse();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        AuthorDAO authorDAO = new AuthorDAOImpl(dataBaseConnection);
        authorService = new AuthorService(authorDAO, AuthorMapper.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertAuthor(req, resp);
                    break;
                case "/update":
                    updateAuthor(req, resp);
                    break;
                case "/delete":
                    deleteAuthor(req, resp);
                    break;
                case "/list":
                    getAllAuthors(resp);
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
            AuthorDTO author = authorService.findById(id);
            if (author == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Author not found");
            } else {
                jsonResponse.sendJsonResponse(resp, author);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void getAllAuthors(HttpServletResponse resp) throws IOException {
        List<AuthorDTO> authors = authorService.findAll();
        jsonResponse.sendJsonResponse(resp, authors);
    }

    private void deleteAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            authorService.delete(id);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void updateAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        String nameParam = req.getParameter("name");

        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        if (isNullOrEmpty(nameParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'name' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            AuthorDTO author = authorService.findById(id);
            if (author == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Author not found");
            } else {
                author.setName(nameParam);
                authorService.update(author);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void insertAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nameParam = req.getParameter("name");
        if (isNullOrEmpty(nameParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'name' parameter");
            return;
        }

        try {
            authorService.add(nameParam);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (RuntimeException e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the author");
        }
    }
}