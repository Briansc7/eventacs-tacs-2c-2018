package com.eventacs.external.eventbrite.model;

public class EventResponse {

    private NameResponse name;
    private String id;
    private String category;

    public EventResponse() {

    }

    public EventResponse(NameResponse name, String id, String category) {
        this.name = name;
        this.id = id;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
