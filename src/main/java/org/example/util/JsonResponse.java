package org.example.util;

import jakarta.servlet.http.HttpServletResponse;
import org.example.config.JacksonConfig;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonResponse {

    public void sendJsonResponse(HttpServletResponse resp, Object obj) throws IOException {
        String json = JacksonConfig.createObjectMapper().writeValueAsString(obj);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.print(json);
            out.flush();
        }
    }
}
