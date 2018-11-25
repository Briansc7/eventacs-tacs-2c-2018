package com.eventacs.event.dto;

import org.mongodb.morphia.annotations.Id;

import java.util.List;

public class EventListDTO {

    @Id
    private String _id;
    private String listId;
    private String listName;
    private List<EventDTO> events;

    public EventListDTO() {

    }

    public EventListDTO(String listId, String listName, List<EventDTO> events) {
        this.listId = listId;
        this.listName = listName;
        this.events = events;
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

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
