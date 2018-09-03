package com.eventacs.event.model;

public class Event {

    private String id;
    private String name;
    private String categorie;

    public Event(String id, String name, String categorie) {
        this.id = id;
        this.name = name;
        this.categorie = categorie;
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
