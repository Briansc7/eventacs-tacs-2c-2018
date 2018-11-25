package com.eventacs.event.dto;

import java.util.Date;

public class EventDTO {

    private String id;
    private String name;
    private String description;
    private String category;
    private String logoUrl;


    private Date start;
    private Date end;

    public EventDTO() {
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public EventDTO(String id, String name, String description, String category, String logoUrl, Date start, Date end) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.logoUrl = logoUrl;
        this.start = start;
        this.end = end;
    }
}
