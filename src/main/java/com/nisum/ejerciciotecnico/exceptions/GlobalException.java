package com.nisum.ejerciciotecnico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleMyException(ValidationException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error: " + e.getMessage());
    }
}
