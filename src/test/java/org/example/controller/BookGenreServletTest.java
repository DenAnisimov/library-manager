package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.BookGenreDTO;
import org.example.service.BookGenreService;
import org.example.util.JsonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class BookGenreServletTest {

    @Mock
    private BookGenreService bookGenreService;

    @Mock
    private JsonResponse jsonResponse;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private BookGenreServlet bookGenreServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() throws Exception {
        when(request.getPathInfo()).thenReturn("/list");
        when(bookGenreService.getAll()).thenReturn(new BookGenreDTO());

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, times(1)).getAll();
        verify(jsonResponse, times(1)).sendJsonResponse(response, new BookGenreDTO());
    }

    @Test
    void testGetAllByGenreId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getByGenreId");
        when(request.getParameter("genreId")).thenReturn("1");

        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        when(bookGenreService.getAllByGenreId(1)).thenReturn(bookGenreDTO);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, times(1)).getAllByGenreId(1);
        verify(jsonResponse, times(1)).sendJsonResponse(response, bookGenreDTO);
    }

    @Test
    void testGetAllByGenreIdMissingParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getByGenreId");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).getAllByGenreId(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'genre id' parameter");
    }

    @Test
    void testGetAllByGenreIdInvalidParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getByGenreId");
        when(request.getParameter("genreId")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).getAllByGenreId(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'genre id' parameter");
    }

    @Test
    void testGetAllByBookId() throws Exception {
        when(request.getPathInfo()).thenReturn("/getByBookId");
        when(request.getParameter("bookId")).thenReturn("1");

        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        when(bookGenreService.getAllByBookId(1)).thenReturn(bookGenreDTO);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, times(1)).getAllByBookId(1);
        verify(jsonResponse, times(1)).sendJsonResponse(response, bookGenreDTO);
    }

    @Test
    void testGetAllByBookIdMissingParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getByBookId");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).getAllByBookId(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'book id' parameter");
    }

    @Test
    void testGetAllByBookIdInvalidParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getByBookId");
        when(request.getParameter("bookId")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).getAllByBookId(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'book id' parameter");
    }

    @Test
    void testInsertBookGenre() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");
        when(request.getParameter("bookId")).thenReturn("1");
        when(request.getParameter("genreId")).thenReturn("2");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, times(1)).add(1, 2);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testInsertBookGenreMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).add(anyInt(), anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'book id' parameter");
    }

    @Test
    void testInsertBookGenreInvalidParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");
        when(request.getParameter("bookId")).thenReturn("invalid");
        when(request.getParameter("genreId")).thenReturn("2");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).add(anyInt(), anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
    }

    @Test
    void testDeleteBookGenre() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("bookId")).thenReturn("1");
        when(request.getParameter("genreId")).thenReturn("2");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, times(1)).delete(1, 2);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDeleteBookGenreMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).delete(anyInt(), anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'book id' parameter");
    }

    @Test
    void testDeleteBookGenreInvalidParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("bookId")).thenReturn("invalid");
        when(request.getParameter("genreId")).thenReturn("2");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookGenreServlet.doGet(request, response);

        verify(bookGenreService, never()).delete(anyInt(), anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
    }
}