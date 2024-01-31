package com.jrs296.ems.models.DTOs.OutputDTOs

import groovy.transform.Canonical;
import org.springframework.stereotype.Component;

@Component
@Canonical
public class ResponseObject <T>{
    private String message;
    private int statusCode;
    private T data;
    public ResponseObject(){}

    public ResponseObject(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseObject(String message, int statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }
}

