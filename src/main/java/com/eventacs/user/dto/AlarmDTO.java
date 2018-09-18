package com.eventacs.user.dto;

import java.util.Optional;

public class AlarmDTO {

    private Optional<String> id;
    private String userId;
    private SearchDTO searchDTO;

    public AlarmDTO() {

    }

    public AlarmDTO(String userId, SearchDTO searchDTO) {
        this.userId = userId;
        this.searchDTO = searchDTO;
    }

    public AlarmDTO(Optional<String> id, String userId, SearchDTO searchDTO) {
        this.id = id;
        this.userId = userId;
        this.searchDTO = searchDTO;
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

    public SearchDTO getSearchDTO() {
        return searchDTO;
    }

    public void setSearchDTO(SearchDTO searchDTO) {
        this.searchDTO = searchDTO;
    }

}
