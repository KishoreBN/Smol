package com.primeengineer.smol.exception;

import com.primeengineer.smol.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortUrlNotFound.class)
    public ResponseEntity<ExceptionResponse> shortUrlNotFound(ShortUrlNotFound ex) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .status(500L)
                .timestamp(LocalDateTime.now())
                .error(ex.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> usernameNotFound(UsernameNotFoundException ex) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .status(403L)
                .timestamp(LocalDateTime.now())
                .error(ex.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> usernameNotFound(UserAlreadyExists ex) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .status(500L)
                .timestamp(LocalDateTime.now())
                .error(ex.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
