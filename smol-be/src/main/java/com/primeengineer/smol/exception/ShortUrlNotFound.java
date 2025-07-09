package com.primeengineer.smol.exception;

public class ShortUrlNotFound extends RuntimeException{
    public ShortUrlNotFound(String msg) {
        super(msg);
    }
}
