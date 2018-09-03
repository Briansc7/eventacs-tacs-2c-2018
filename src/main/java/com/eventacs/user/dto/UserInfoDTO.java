package com.eventacs.user.dto;

public class UserInfoDTO {

    private String id;
    private String name;
    private String lastName;
    //TODO LISTA DE VEENTOS??

    public UserInfoDTO() {

    }

    public UserInfoDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
