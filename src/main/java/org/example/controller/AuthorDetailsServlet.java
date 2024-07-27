package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.DataBaseConnection;
import org.example.dao.AuthorDAO;
import org.example.dao.AuthorDAOImpl;
import org.example.dao.AuthorDetailsDAO;
import org.example.dao.AuthorDetailsDAOImpl;
import org.example.dto.AuthorDTO;
import org.example.dto.AuthorDetailsDTO;
import org.example.map.AuthorDetailsMapper;
import org.example.map.AuthorMapper;
import org.example.service.AuthorDetailsService;
import org.example.service.AuthorService;
import org.example.util.JsonResponse;

import java.io.IOException;
import java.util.List;

import static org.example.util.ServletUtil.isNullOrEmpty;
import static org.example.util.ServletUtil.sendError;

@WebServlet("/authordetails/*")
public class AuthorDetailsServlet extends HttpServlet {
    private AuthorDetailsService authorDetailsService;
    private AuthorService authorService;
    private JsonResponse jsonResponse;

    @Override
    public void init() throws ServletException {
        jsonResponse = new JsonResponse();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        AuthorDAO authorDAO = new AuthorDAOImpl(dataBaseConnection);
        AuthorDetailsDAO authorDetailsDAO = new AuthorDetailsDAOImpl(dataBaseConnection, authorDAO);
        authorDetailsService = new AuthorDetailsService(authorDetailsDAO, authorDAO,
                AuthorDetailsMapper.INSTANCE, AuthorMapper.INSTANCE);
        authorService = new AuthorService(authorDAO, AuthorMapper.INSTANCE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getPathInfo();
        if (action == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing action");
            return;
        }

        try {
            switch (action) {
                case "/insert":
                    insertAuthorDetails(req, resp);
                    break;
                case "/update":
                    updateAuthorDetails(req, resp);
                    break;
                case "/delete":
                    deleteAuthorDetails(req, resp);
                    break;
                case "/list":
                    getAllAuthorDetails(resp);
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
            AuthorDetailsDTO authorDetails = authorDetailsService.findById(id);
            if (authorDetails == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "AuthorDetails not found");
            } else {
                jsonResponse.sendJsonResponse(resp, authorDetails);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void getAllAuthorDetails(HttpServletResponse resp) throws IOException {
        List<AuthorDetailsDTO> authorDetails = authorDetailsService.findAll();
        jsonResponse.sendJsonResponse(resp, authorDetails);
    }

    private void deleteAuthorDetails(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            authorDetailsService.delete(id);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void updateAuthorDetails(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        String briefBiographyParam = req.getParameter("briefBiography");
        String lifeYearsParam = req.getParameter("lifeYears");
        String authorIdParam = req.getParameter("authorId");

        if (isNullOrEmpty(idParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
            return;
        }

        if (isNullOrEmpty(briefBiographyParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'brief biography' parameter");
            return;
        }

        if (isNullOrEmpty(lifeYearsParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'life years' parameter");
            return;
        }

        if (isNullOrEmpty(authorIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'authorIdParam' parameter");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            AuthorDetailsDTO authorDetails = authorDetailsService.findById(id);
            if (authorDetails == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "AuthorDetails not found");
            } else {
                authorDetails.setBriefBiography(briefBiographyParam);
                authorDetails.setLifeYears(lifeYearsParam);
                int authorId = Integer.parseInt(authorIdParam);
                AuthorDTO author = authorService.findById(authorId);
                authorDetails.setAuthorDTO(author);
                authorDetailsService.update(authorDetails);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
        }
    }

    private void insertAuthorDetails(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String briefBiographyParam = req.getParameter("briefBiography");
        String lifeYearsParam = req.getParameter("lifeYears");
        String authorIdParam = req.getParameter("authorId");

        if (isNullOrEmpty(briefBiographyParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'brief biography' parameter");
            return;
        }

        if (isNullOrEmpty(lifeYearsParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'life years' parameter");
            return;
        }

        if (isNullOrEmpty(authorIdParam)) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Missing 'authorIdParam' parameter");
            return;
        }

        try {
            int authorId = Integer.parseInt(authorIdParam);
            authorDetailsService.add(briefBiographyParam, lifeYearsParam, authorId);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (NumberFormatException e) {
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while inserting the author");
        }
    }
}