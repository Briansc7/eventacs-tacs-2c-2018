package com.eventacs.event.model;

public class Event {

    private String id; //ES EL ID DE EVENTBRITE (ver si en futuro manejar 1 nuestro y uno de eventbrite)
    private String name;
    private String category;

    public Event(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String categorie) {
        this.category = categorie;
    }
}
