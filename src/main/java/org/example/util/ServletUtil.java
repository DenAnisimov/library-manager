package org.example.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletUtil {
    
    private ServletUtil() {}

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static void sendError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.sendError(statusCode, message);
    }
}
