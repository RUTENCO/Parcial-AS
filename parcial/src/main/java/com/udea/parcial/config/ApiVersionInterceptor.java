package com.udea.parcial.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiVersionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Ignorar validación para Swagger
        if (request.getRequestURI().contains("/swagger-ui") || request.getRequestURI().contains("/v3/api-docs")) {
            return true;
        }

        String version = request.getHeader("X-API-Version");

        // Validación estricta
        if (version == null || !version.equals("v1")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Header 'X-API-Version: v1' es requerido\"}");
            response.setContentType("application/json");
            return false; // Bloquea la petición
        }

        return true; // Deja pasar la petición al Controller
    }
}
