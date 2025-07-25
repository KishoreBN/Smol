package com.primeengineer.smol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDoesNotExist extends RuntimeException{
    public UserDoesNotExist() {
        super("User does not exist.");
    }
    public UserDoesNotExist(String msg) {
        super(msg);
    }
}
