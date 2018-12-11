package com.eventacs.external.eventbrite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventResponse {

    private NameResponse name;
    private String id;
    private DescriptionResponse description;
    @JsonProperty("category_id")
    private String category;
    private EventDate start;
    private EventDate end;
    private Logo logo;
    private LocalDateTime changed;

    public EventResponse() {

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

    public DescriptionResponse getDescription() {
        return description;
    }

    public void setDescription(DescriptionResponse description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public EventDate getStart() {
        return start;
    }

    public void setStart(EventDate start) {
        this.start = start;
    }

    public EventDate getEnd() {
        return end;
    }

    public void setEnd(EventDate end) {
        this.end = end;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public EventResponse(NameResponse name, String id, DescriptionResponse description, String category, EventDate start, EventDate end, Logo logo, LocalDateTime changed) {

        this.name = name;
        this.id = id;
        this.description = description;
        this.category = category;
        this.start = start;
        this.end = end;
        this.logo = logo;
        this.changed = changed;
    }
}
