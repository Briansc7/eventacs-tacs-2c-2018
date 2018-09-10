package com.eventacs.exception;

public class ConectionErrorException extends RuntimeException {

    public ConectionErrorException(String message, Exception e) {
        super(message,e);
    }

    public ConectionErrorException(String message) {
        super(message);
    }
}
