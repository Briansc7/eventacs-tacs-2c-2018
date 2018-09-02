package com.eventacs.user.dto;

public class AlarmDTO {

    private String id;
    private String userId;
    private SearchDTO searchDTO;

    public AlarmDTO() {

    }

    public AlarmDTO(String id, String userId, SearchDTO searchDTO) {
        this.id = id;
        this.userId = userId;
        this.searchDTO = searchDTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
