package com.eventacs.user.model;

public class UserId {
    private String userId;

    public UserId() {
    }

    public UserId(String userId) {

        this.userId = userId;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
