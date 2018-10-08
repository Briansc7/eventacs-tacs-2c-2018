package com.eventacs.user.exception;

public class AlarmCreationError extends RuntimeException {

    public AlarmCreationError(String message, Exception e) {
        super(message,e);
    }

    public AlarmCreationError(String message) {
        super(message);
    }

}
