package com.eventacs.event.model;


import java.util.List;

public class EventList {
    private Long listId;
    private String listName;
    private String userName;
    private List<Event> events;

    public EventList(Long listId, String listName, List<Event> events) {
        this.listId = listId;
        this.listName = listName;
        this.events = events;
    }

    public EventList() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
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
