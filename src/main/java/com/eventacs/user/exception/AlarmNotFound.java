package com.eventacs.user.exception;

public class AlarmNotFound extends RuntimeException {

    public AlarmNotFound(String message, Exception e) {
        super(message,e);
    }

    public AlarmNotFound(String message) {
        super(message);
    }

}