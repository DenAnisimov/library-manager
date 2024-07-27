package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.DataBaseConnection;
import org.example.dao.GenreDAO;
import org.example.dao.GenreDAOImpl;
import org.example.dto.GenreDTO;
import org.example.map.GenreMapper;
import org.example.service.GenreService;
import org.example.util.JsonResponse;

import java.io.IOException;
import java.util.List;

import static org.example.util.ServletUtil.isNullOrEmpty;
import static org.example.util.ServletUtil.sendError;

@WebServlet("/genre/*")
public class GenreServlet extends HttpServlet {
    private GenreService genreService;
    private JsonResponse jsonResponse;

    @Override
    public void init() throws ServletException {
        jsonResponse = new JsonResponse();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        GenreDAO genreDAO = new GenreDAOImpl(dataBaseConnection);
        genreService = new GenreService(genreDAO, GenreMapper.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertGenre(req, resp);
                    break;
                case "/update":
                    updateGenre(req, resp);
                    break;
                case "/delete":
                    deleteGenre(req, resp);
                    break;
                case "/list":
                    getAllGenres(resp);
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
            GenreDTO genre = genreService.findById(id);
            if (genre == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Genre not found");
            } else {
               jsonResponse.sendJsonResponse(resp, genre);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void getAllGenres(HttpServletResponse resp) throws IOException {
        List<GenreDTO> genres = genreService.findAll();
        jsonResponse.sendJsonResponse(resp, genres);
    }

    private void deleteGenre(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            genreService.delete(id);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void updateGenre(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            GenreDTO genre = genreService.findById(id);
            if (genre == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Genre not found");
            } else {
                genre.setName(nameParam);
                genreService.update(genre);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void insertGenre(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nameParam = req.getParameter("name");
        if (isNullOrEmpty(nameParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'name' parameter");
            return;
        }

        try {
            genreService.add(nameParam);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (RuntimeException e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the genre");
        }
    }
}
