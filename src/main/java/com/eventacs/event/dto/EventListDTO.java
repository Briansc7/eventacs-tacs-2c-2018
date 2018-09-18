package com.eventacs.event.dto;

import com.eventacs.event.model.Event;

import java.util.List;

public class EventListDTO {

    private String id;
    private String listName;
    private List<Event> events;

    public EventListDTO() {

    }

    public EventListDTO(String id, String listName, List<Event> events) {
        this.id = id;
        this.listName = listName;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
