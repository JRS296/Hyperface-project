package com.jrs296.ems.models.DTOs.OutputDTOs

import groovy.transform.Canonical;
import org.springframework.stereotype.Component;

@Component
@Canonical
public class ResponseObject <T>{
    private String message;
    private int statusCode;
    private T data;

    public ResponseObject(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data
    }

    public ResponseObject(String message, int statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseObject() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

