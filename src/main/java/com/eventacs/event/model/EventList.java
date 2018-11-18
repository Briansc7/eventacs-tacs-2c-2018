package com.eventacs.event.model;

import java.util.List;

public class EventList {
    private String id;
    private String listName;
    private List<Event> events;

    public EventList(String id, String listName, List<Event> events) {
        this.id = id;
        this.listName = listName;
        this.events = events;
    }

    public EventList() {
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
