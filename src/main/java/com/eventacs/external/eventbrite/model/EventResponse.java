package com.eventacs.external.eventbrite.model;

public class EventResponse {

    private NameResponse name;
    private String id;

    public EventResponse() {

    }

    public EventResponse(NameResponse name, String id) {
        this.name = name;
        this.id = id;
    }

    public NameResponse getName() {
        return name;
    }

    public void setName(NameResponse name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
