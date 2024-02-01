package com.jrs296.ems.MiscellaneousTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrs296.ems.config.security.AuthEntry;
import com.jrs296.ems.config.security.ForbiddenHandler;
import com.jrs296.ems.exceptions.GlobalExceptionHandler;
import com.jrs296.ems.exceptions.UserNotFoundException;
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import java.security.SignatureException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionTests {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleValidationErrors() {
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, null);
        ResponseEntity<Map<String, List<String>>> responseEntity = exceptionHandler.handleValidationErrors(ex);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList("message"), responseEntity.getBody().get("errors"));
    }

    @Test
    void handleNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException("User not found");
        ResponseEntity<Map<String, List<String>>> responseEntity = exceptionHandler.handleNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList("User not found"), responseEntity.getBody().get("errors"));
    }

    @Test
    void handleGeneralExceptions() {
        Exception ex = new Exception("General exception");
        ResponseEntity<Map<String, List<String>>> responseEntity = exceptionHandler.handleGeneralExceptions(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList("General exception"), responseEntity.getBody().get("errors"));
    }

    @Test
    void handleRuntimeExceptions() {
        RuntimeException ex = new RuntimeException("Runtime exception");
        ResponseEntity<Map<String, List<String>>> responseEntity = exceptionHandler.handleRuntimeExceptions(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList("Runtime exception"), responseEntity.getBody().get("errors"));
    }

    @Test
    void handleSignatureExceptions() {
        SignatureException ex = new SignatureException("Signature exception");
        ResponseEntity<Map<String, List<String>>> responseEntity = exceptionHandler.handleSignatureExceptions(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList("Signature exception"), responseEntity.getBody().get("errors"));
    }
}

class AuthEntryTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void commence_WithBadCredentialsException_ShouldReturnInvalidCredentialsResponse() throws IOException {
        // Arrange
        AuthEntry authEntry = new AuthEntry();
        objectMapper = new ObjectMapper();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        AuthenticationException authException = new BadCredentialsException("Invalid username or password");

        // Act
        authEntry.commence(null, mockResponse, authException);

        // Assert
        assertEquals(401, mockResponse.getStatus());
        assertEquals("bad_credentials", mockResponse.getHeader("access_denied_reason"));
        ResponseObject responseObject = new ObjectMapper().readValue(mockResponse.getContentAsString(), ResponseObject.class);
        assertEquals("Invalid Credentials", responseObject.getMessage());
        assertEquals(401, responseObject.getStatusCode());
        assertEquals("Invalid username or password", responseObject.getData());
    }

    @Test
    void commence_WithOtherAuthenticationException_ShouldReturnInvalidTokenResponse() throws IOException {
        // Arrange
        AuthEntry authEntry = new AuthEntry();
        objectMapper = new ObjectMapper();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        AuthenticationException authException = new AuthenticationException("Invalid token") {};

        // Act
        authEntry.commence(null, mockResponse, authException);

        // Assert
        assertEquals(401, mockResponse.getStatus());
        assertEquals("authentication_required", mockResponse.getHeader("access_denied_reason"));
        ResponseObject responseObject = new ObjectMapper().readValue(mockResponse.getContentAsString(), ResponseObject.class);
        assertEquals("Invalid Token", responseObject.getMessage());
        assertEquals(401, responseObject.getStatusCode());
        assertEquals("Invalid token", responseObject.getData());
    }

    @Test
    void handle_ShouldReturnForbiddenResponse() throws IOException {
        // Arrange
        ForbiddenHandler forbiddenHandler = new ForbiddenHandler();
        objectMapper = new ObjectMapper();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        AccessDeniedException accessDeniedException = new AccessDeniedException("Access is denied");

        // Act
        forbiddenHandler.handle(null, mockResponse, accessDeniedException);

        // Assert
        assertEquals(403, mockResponse.getStatus());
        ResponseObject responseObject = new ObjectMapper().readValue(mockResponse.getContentAsString(), ResponseObject.class);
        assertEquals("Unauthorized Request", responseObject.getMessage());
        assertEquals(403, responseObject.getStatusCode());
        assertEquals("Access is denied", responseObject.getData());
    }
}