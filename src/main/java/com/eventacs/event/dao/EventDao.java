package com.eventacs.event.dao;

import org.mongodb.morphia.annotations.Property;

import java.time.LocalDateTime;

public class EventDao {

    private String eventId;
    private String name;
    private String description;
    private String category;
    private String logoUrl;
    @Property("startDate")
    private LocalDateTime start;
    @Property("endDate")
    private LocalDateTime end;

    public EventDao(String eventId, String name, String description, String category, String logoUrl, LocalDateTime start, LocalDateTime end) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.logoUrl = logoUrl;
        this.start = start;
        this.end = end;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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
}
