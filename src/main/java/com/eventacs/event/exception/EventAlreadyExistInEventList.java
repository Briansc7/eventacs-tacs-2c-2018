package com.eventacs.event.exception;

public class EventAlreadyExistInEventList extends RuntimeException {

    public EventAlreadyExistInEventList(String message, Exception e) {
        super(message,e);
    }

    public EventAlreadyExistInEventList(String message) {
        super(message);
    }
}
