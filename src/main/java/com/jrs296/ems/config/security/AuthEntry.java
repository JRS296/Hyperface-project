package com.jrs296.ems.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntry implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        response.setStatus(401);
        if (authException.getClass().equals(BadCredentialsException.class)) {
            response.addHeader("access_denied_reason", "bad_credentials");
            ResponseObject res = new ResponseObject("Invalid Credentials", 401, authException.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(res));
        } else {
            response.addHeader("access_denied_reason", "authentication_required");
            ResponseObject res = new ResponseObject("Invalid Token", 401, authException.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(res));
        }
    }
}
