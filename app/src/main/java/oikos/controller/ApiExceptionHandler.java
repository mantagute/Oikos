package oikos.controller;

import java.time.Instant;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import oikos.api.erro.ApiErroResponse;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErroResponse> handleIllegalArgument(IllegalArgumentException exception, HttpServletRequest request) {
    
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String mensagem = exception.getMessage();
    
        return construirResponse(status, mensagem, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErroResponse> handleNoSuchElement(NoSuchElementException exception, HttpServletRequest request) {
    
        HttpStatus status = HttpStatus.NOT_FOUND;
        String mensagem = exception.getMessage();
    
        return construirResponse(status, mensagem, request);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiErroResponse> handleSecurityException(SecurityException exception, HttpServletRequest request) {
    
        HttpStatus status = HttpStatus.FORBIDDEN;
        String mensagem = exception.getMessage();
    
        return construirResponse(status, mensagem, request);
    }

    private ResponseEntity<ApiErroResponse> construirResponse(HttpStatus status, String mensagem, HttpServletRequest request) {

        ApiErroResponse response = new ApiErroResponse(Instant.now(), status.value(), status.getReasonPhrase(), mensagem, request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }
}   
