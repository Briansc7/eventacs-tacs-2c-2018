package com.eventacs.user.exception;

public class EventListNotFound extends RuntimeException {

    public EventListNotFound(String message, Exception e) {
        super(message,e);
    }

    public EventListNotFound(String message) {
        super(message);
    }

}
