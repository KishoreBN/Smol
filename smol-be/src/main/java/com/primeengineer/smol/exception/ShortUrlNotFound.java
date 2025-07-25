package com.primeengineer.smol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShortUrlNotFound extends RuntimeException{
    public ShortUrlNotFound(String msg) {
        super(msg);
    }
}
