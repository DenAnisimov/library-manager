package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.BookDTO;
import org.example.service.BookService;
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

class BookServletTest {

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @Mock
    private JsonResponse jsonResponse;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private BookServlet bookServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(request.getPathInfo()).thenReturn("/list");
        when(bookService.findAll()).thenReturn(Collections.singletonList(new BookDTO()));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, times(1)).findAll();
        verify(jsonResponse, times(1)).sendJsonResponse(response, Collections.singletonList(new BookDTO()));
    }

    @Test
    void testGetAllBooksByAuthor() throws Exception {
        when(request.getPathInfo()).thenReturn("/listByAuthor");
        when(request.getParameter("author_id")).thenReturn("1");

        BookDTO bookDTO = new BookDTO();
        when(bookService.findAllByAuthor(1)).thenReturn(Collections.singletonList(bookDTO));

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, times(1)).findAllByAuthor(1);
        verify(jsonResponse, times(1)).sendJsonResponse(response, Collections.singletonList(bookDTO));
    }

    @Test
    void testGetAllBooksByAuthorMissingParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/listByAuthor");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).findAllByAuthor(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'author_id' parameter");
    }

    @Test
    void testGetAllBooksByAuthorInvalidParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/listByAuthor");
        when(request.getParameter("author_id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).findAllByAuthor(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testGetById() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("1");

        BookDTO bookDTO = new BookDTO();
        when(bookService.findById(1)).thenReturn(bookDTO);

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, times(1)).findById(1);
        verify(jsonResponse, times(1)).sendJsonResponse(response, bookDTO);
    }

    @Test
    void testGetByIdMissingParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testGetByIdInvalidParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/getById");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).findById(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testDeleteBook() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("1");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, times(1)).delete(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDeleteBookMissingParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testDeleteBookInvalidParameter() throws Exception {
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("invalid");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).delete(anyInt());
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testUpdateBook() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("author_id")).thenReturn("2");
        when(request.getParameter("title")).thenReturn("Title");
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("publication_date")).thenReturn("2024/07/27");

        BookDTO bookDTO = new BookDTO();
        when(bookService.findById(1)).thenReturn(bookDTO);
        when(authorService.findById(2)).thenReturn(null); // Assuming findById returns null for valid ID

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, times(1)).update(bookDTO);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testUpdateBookMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).update(any(BookDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'id' parameter");
    }

    @Test
    void testUpdateBookInvalidParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn("invalid");
        when(request.getParameter("author_id")).thenReturn("invalid");
        when(request.getParameter("title")).thenReturn("Title");
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("publication_date")).thenReturn("2024/07/27");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).update(any(BookDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'id' parameter");
    }

    @Test
    void testInsertBook() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");
        when(request.getParameter("author_id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("Title");
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("publication_date")).thenReturn("2024/07/27");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, times(1)).add(any(BookDTO.class));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testInsertBookMissingParameters() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).add(any(BookDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'author_id' parameter");
    }

    @Test
    void testInsertBookInvalidDate() throws Exception {
        when(request.getPathInfo()).thenReturn("/insert");
        when(request.getParameter("author_id")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("Title");
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("publication_date")).thenReturn("invalid_date");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doGet(request, response);

        verify(bookService, never()).add(any(BookDTO.class));
        verify(response, times(1)).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid 'publication date' format");
    }
}
