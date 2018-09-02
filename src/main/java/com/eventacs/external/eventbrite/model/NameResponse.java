package com.eventacs.external.eventbrite.model;

public class NameResponse {

    private String text;

    public NameResponse() {

    }

    public NameResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
