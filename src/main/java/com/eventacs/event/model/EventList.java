package com.eventacs.event.model;


import java.util.List;

public class EventList {
    private String listId;
    private String listName;
    private List<Event> events;

    public EventList(String listId, String listName, List<Event> events) {
        this.listId = listId;
        this.listName = listName;
        this.events = events;
    }

    public EventList() {
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
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
