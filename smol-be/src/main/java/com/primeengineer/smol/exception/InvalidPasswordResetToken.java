package com.primeengineer.smol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordResetToken extends RuntimeException{
    public InvalidPasswordResetToken() {
        super("Invalid Reset Token.");
    }
}
