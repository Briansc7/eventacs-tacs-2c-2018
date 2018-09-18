package com.eventacs.user.model;

public class Alarm {

    private String id;
    private String userId;
    private Search search;

    public Alarm(String id, String userId, Search search) {
        this.id = id;
        this.userId = userId;
        this.search = search;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
