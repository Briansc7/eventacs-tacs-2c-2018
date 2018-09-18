package com.eventacs.user.exception;

public class UserNotFound extends RuntimeException {

    public UserNotFound(String message, Exception e) {
        super(message,e);
    }

    public UserNotFound(String message) {
        super(message);
    }

}
