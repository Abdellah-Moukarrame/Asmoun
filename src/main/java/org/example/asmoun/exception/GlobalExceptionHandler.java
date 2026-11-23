package org.example.asmoun.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpServletRequest request,
            HttpStatus status,
            String message
    ) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message,
                "path", request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    //404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                request,
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }
    //400
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidValue(
            InvalidValueException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                request,
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }
    //409
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAlreadyExists(
            ResourceAlreadyExistException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                request,
                HttpStatus.CONFLICT,
                exception.getMessage()
        );
    }
    //400
    @ExceptionHandler(RequiredArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleRequiredArgument(
            RequiredArgumentException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                request,
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }
}