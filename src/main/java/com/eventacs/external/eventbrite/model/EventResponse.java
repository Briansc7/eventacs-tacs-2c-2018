package com.eventacs.external.eventbrite.model;

public class EventResponse {

    private NameResponse name;
    private String id;
    private String description;
    private String category;
    private String start;
    private String end;
    private String logoUrl;

    public EventResponse() {

    }

    public EventResponse(NameResponse name, String id, String description, String category, String start, String end, String logoUrl) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.category = category;
        this.start = start;
        this.end = end;
        this.logoUrl = logoUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
