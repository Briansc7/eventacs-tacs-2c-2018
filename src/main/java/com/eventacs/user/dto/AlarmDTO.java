package com.eventacs.user.dto;

import java.util.Optional;

public class AlarmDTO {

    private Optional<String> id;
    private String userId;
    private SearchDTO search;

    public AlarmDTO() {

    }

    public AlarmDTO(String userId, SearchDTO search) {
        this.userId = userId;
        this.search = search;
    }

    public AlarmDTO(Optional<String> id, String userId, SearchDTO search) {
        this.id = id;
        this.userId = userId;
        this.search = search;
    }

    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SearchDTO getSearch() {
        return search;
    }

    public void setSearch(SearchDTO search) {
        this.search = search;
    }

}
