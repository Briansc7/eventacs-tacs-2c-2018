package com.eventacs.user.dto;

import com.eventacs.event.model.EventList;

import java.util.List;

public class UserInfoDTO {

    private String id;
    private String name;
    private String lastName;
    private List<EventList> events;

    public UserInfoDTO() {

    }

    public UserInfoDTO(String id, String name, String lastName, List<EventList> events) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.events = events;
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
}
