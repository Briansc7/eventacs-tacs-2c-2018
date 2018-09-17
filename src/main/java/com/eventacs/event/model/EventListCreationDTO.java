package com.eventacs.event.model;

public class EventListCreationDTO {
    private String userId;
    private String listName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public EventListCreationDTO(String userId, String listName) {

        this.userId = userId;
        this.listName = listName;
    }

    public EventListCreationDTO() {

    }
}
