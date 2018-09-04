package com.eventacs.account.dto;

public class UserLoginDTO {

    private String name;
    private String encryptedPassword;

    public UserLoginDTO() {

    }

    public UserLoginDTO(String name, String encryptedPassword) {
        this.name = name;
        this.encryptedPassword = encryptedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

}
