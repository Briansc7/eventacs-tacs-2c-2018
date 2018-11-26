package com.eventacs.event.exception;

public class EventListAlreadyExists extends RuntimeException {

    public EventListAlreadyExists(String message, Exception e) {
        super(message,e);
    }

    public EventListAlreadyExists(String message) {
        super(message);
    }

}
