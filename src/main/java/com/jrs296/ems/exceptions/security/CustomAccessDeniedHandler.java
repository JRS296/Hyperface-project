package com.jrs296.ems.exceptions.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ResponseObject res= new ResponseObject("User not authorized for this route",401,accessDeniedException.getMessage());
        response.setStatus(401);
        response.getWriter().write(objectMapper.writeValueAsString(res));
    }
}