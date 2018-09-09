package com.eventacs.event.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {

    private String id; //ES EL ID DE EVENTBRITE (ver si en futuro manejar 1 nuestro y uno de eventbrite)
    private String name;
    private String description;
    private String category;
    private LocalDateTime start;
    private LocalDateTime end;
    private String logoUrl;

    public Event() {
    }

    public Event(String id, String name, String description, String category, LocalDateTime start, LocalDateTime end, String logoUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.start = start;
        this.end = end;
        this.logoUrl = logoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
