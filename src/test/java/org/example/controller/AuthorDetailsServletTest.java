package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.AuthorDTO;
import org.example.dto.AuthorDetailsDTO;
import org.example.service.AuthorDetailsService;
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

class AuthorDetailsServletTest {

    @Mock
    private AuthorDetailsService authorDetailsService;

    @Mock
    private AuthorService authorService;

    @Mock
    private JsonResponse jsonResponse;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthorDetailsServlet authorDetailsServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthorDetails() throws Exception {
        when(request.getPathInfo()).thenReturn("/list");
        when(authorDetailsService.findAll()).thenReturn(Collections.singletonList(new AuthorDetailsDTO()));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, times(1)).findAll();
        verify(jsonResponse, times(1)).sendJsonResponse(response, Collections.singletonList(new AuthorDetailsDTO()));
    }

    @Test
    void testGetByIdValidId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("1");

        AuthorDetailsDTO authorDetails = new AuthorDetailsDTO();
        when(authorDetailsService.findById(1)).thenReturn(authorDetails);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, times(1)).findById(1);
        verify(jsonResponse, times(1)).sendJsonResponse(response, authorDetails);
    }

    @Test
    void testGetByIdInvalidId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testGetByIdMissingId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testInsertAuthorDetails() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");
        when(request.getParameter("briefBiography")).thenReturn("Biography");
        when(request.getParameter("lifeYears")).thenReturn("1900-2000");
        when(request.getParameter("authorId")).thenReturn("1");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, times(1)).add("Biography", "1900-2000", 1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testInsertAuthorDetailsMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, never()).add(anyString(), anyString(), anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'brief biography' parameter");
    }

    @Test
    void testUpdateAuthorDetails() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("briefBiography")).thenReturn("Updated Biography");
        when(request.getParameter("lifeYears")).thenReturn("1901-2001");
        when(request.getParameter("authorId")).thenReturn("2");

        AuthorDetailsDTO authorDetails = new AuthorDetailsDTO();
        when(authorDetailsService.findById(1)).thenReturn(authorDetails);
        AuthorDTO author = new AuthorDTO();
        when(authorService.findById(2)).thenReturn(author);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, times(1)).findById(1);
        verify(authorService, times(1)).findById(2);
        verify(authorDetailsService, times(1)).update(authorDetails);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testUpdateAuthorDetailsMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, never()).update(any(AuthorDetailsDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testDeleteAuthorDetails() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("1");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, times(1)).delete(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDeleteAuthorDetailsInvalidId() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testDeleteAuthorDetailsMissingId() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        authorDetailsServlet.doGet(request, response);

        verify(authorDetailsService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }
}