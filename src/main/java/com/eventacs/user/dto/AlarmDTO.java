package com.eventacs.user.dto;

import java.util.Optional;

public class AlarmDTO {

    private Optional<Long> alarmId;
    private String userId;
    private SearchDTO search;

    public AlarmDTO() {

    }

    public AlarmDTO(String userId, SearchDTO search) {
        this.userId = userId;
        this.search = search;
    }

    public AlarmDTO(Optional<Long> alarmId, String userId, SearchDTO search) {
        this.alarmId = alarmId;
        this.userId = userId;
        this.search = search;
    }

    public Optional<Long> getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Optional<Long> alarmId) {
        this.alarmId = alarmId;
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
