package com.sparta.springintermediateasignment.exceptoins;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PasswordMissmatchException.class})
    public ResponseEntity<String> handlePasswordMissmatchException(PasswordMissmatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
            .getAllErrors()
            .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest()
            .body(errors);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<Map<String, String>> onConstraintValidationException(
        ConstraintViolationException e) {
        var constraintViolations = e.getConstraintViolations();
        Map<String, String> errors = new HashMap<>();
        for (final var constraint : constraintViolations) {

            String message = constraint.getMessage();
            String[] split = constraint.getPropertyPath()
                .toString()
                .split("\\.");
            String propertyPath = split[split.length - 1];
            errors.put(propertyPath, message);

        }
        return ResponseEntity.badRequest()
            .body(errors);
    }
}
