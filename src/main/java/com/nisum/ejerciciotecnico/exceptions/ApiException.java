package com.nisum.ejerciciotecnico.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiException {
    private String message;
    private Map<String, String> errors;

    public ApiException(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
