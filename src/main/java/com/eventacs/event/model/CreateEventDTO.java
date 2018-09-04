package com.eventacs.event.model;

public class CreateEventDTO {
    private String userId;
    private String listName;

    public CreateEventDTO(){}

    public CreateEventDTO(String userId, String listName) {
        this.userId = userId;
        this.listName = listName;
    }

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
}
