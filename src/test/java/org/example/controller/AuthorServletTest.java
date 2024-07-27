package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.AuthorDTO;
import org.example.service.AuthorService;
import org.example.util.JsonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.util.Collections;

import static org.mockito.Mockito.*;

class AuthorServletTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private JsonResponse jsonResponse;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthorServlet authorServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthors() throws Exception {
        when(request.getPathInfo()).thenReturn("/list");
        when(authorService.findAll()).thenReturn(Collections.singletonList(new AuthorDTO()));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, times(1)).findAll();
        verify(jsonResponse, times(1)).sendJsonResponse(response, Collections.singletonList(new AuthorDTO()));
    }

    @Test
    void testGetByIdValidId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("1");

        AuthorDTO author = new AuthorDTO();
        when(authorService.findById(1)).thenReturn(author);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, times(1)).findById(1);
        verify(jsonResponse, times(1)).sendJsonResponse(response, author);
    }

    @Test
    void testGetByIdInvalidId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testGetByIdMissingId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testInsertAuthor() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");
        when(request.getParameter("name")).thenReturn("Author Name");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, times(1)).add("Author Name");
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testInsertAuthorMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, never()).add(anyString());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'name' parameter");
    }

    @Test
    void testUpdateAuthor() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Updated Name");

        AuthorDTO author = new AuthorDTO();
        when(authorService.findById(1)).thenReturn(author);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, times(1)).findById(1);
        verify(authorService, times(1)).update(author);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testUpdateAuthorMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, never()).update(any(AuthorDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testDeleteAuthor() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("1");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, times(1)).delete(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDeleteAuthorInvalidId() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testDeleteAuthorMissingId() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorServlet.doGet(request, response);

        verify(authorService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }
}