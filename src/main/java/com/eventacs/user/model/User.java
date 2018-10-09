package com.eventacs.user.model;

import com.eventacs.event.model.EventList;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String name;
    private String lastName;
    private List<EventList> events;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EventList> getEvents() {
        return events;
    }

    public void setEvents(List<EventList> events) {
        this.events = events;
    }

    public User(String id, String name, String lastName, List<EventList> events, String password) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.events = events;
        this.password = password;
    }

    public void addEventList(String listName, String listId) {
        getEvents().add(new EventList(listId, listName, new ArrayList<>()));
    }

}
