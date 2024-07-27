package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.GenreDTO;
import org.example.service.GenreService;
import org.example.util.JsonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.util.Collections;

import static org.mockito.Mockito.*;

class GenreServletTest {

    @Mock
    private GenreService genreService;

    @Mock
    private JsonResponse jsonResponse;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private GenreServlet genreServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGenres() throws Exception {
        when(request.getPathInfo()).thenReturn("/list");
        when(genreService.findAll()).thenReturn(Collections.singletonList(new GenreDTO()));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, times(1)).findAll();
        verify(jsonResponse, times(1)).sendJsonResponse(response, Collections.singletonList(new GenreDTO()));
    }

    @Test
    void testGetById() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("1");

        GenreDTO genreDTO = new GenreDTO();
        when(genreService.findById(1)).thenReturn(genreDTO);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, times(1)).findById(1);
        verify(jsonResponse, times(1)).sendJsonResponse(response, genreDTO);
    }

    @Test
    void testGetByIdMissingParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testGetByIdInvalidParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testDeleteGenre() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("1");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, times(1)).delete(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDeleteGenreMissingParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testDeleteGenreInvalidParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testUpdateGenre() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Updated Genre");

        GenreDTO genreDTO = new GenreDTO();
        when(genreService.findById(1)).thenReturn(genreDTO);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, times(1)).update(genreDTO);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testUpdateGenreMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, never()).update(any(GenreDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testUpdateGenreInvalidParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, never()).update(any(GenreDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'name' parameter");
    }

    @Test
    void testInsertGenre() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");
        when(request.getParameter("name")).thenReturn("New Genre");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, times(1)).add("New Genre");
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testInsertGenreMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        genreServlet.doGet(request, response);

        verify(genreService, never()).add(anyString());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'name' parameter");
    }
}