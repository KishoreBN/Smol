package com.primeengineer.smol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailDoesNotExist extends RuntimeException{
    public EmailDoesNotExist() {
        super("Email does not exist");
    }
    public EmailDoesNotExist(String message) {
        super(message);
    }
}
