package com.eventacs.user.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserDataDTO {
    private String userName;
    private BigDecimal listCount;
    private BigDecimal alarmsCount;
    private LocalDateTime lastAccess;

    public UserDataDTO() {
    }

    public UserDataDTO(String userName, BigDecimal listCount, BigDecimal alarmsCount, LocalDateTime lastAccess) {
        this.userName = userName;
        this.listCount = listCount;
        this.alarmsCount = alarmsCount;
        this.lastAccess = lastAccess;
    }

    public String getUserName() {
        return userName;
    }

    public UserDataDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public BigDecimal getListCount() {
        return listCount;
    }

    public UserDataDTO setListCount(BigDecimal listCount) {
        this.listCount = listCount;
        return this;
    }

    public BigDecimal getAlarmsCount() {
        return alarmsCount;
    }

    public UserDataDTO setAlarmsCount(BigDecimal alarmsCount) {
        this.alarmsCount = alarmsCount;
        return this;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public UserDataDTO setLastAccess(LocalDateTime lastAccess) {
        this.lastAccess = lastAccess;
        return this;
    }
}