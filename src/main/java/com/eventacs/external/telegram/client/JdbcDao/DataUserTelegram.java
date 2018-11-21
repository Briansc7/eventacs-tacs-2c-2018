package com.eventacs.external.telegram.client.JdbcDao;

public class DataUserTelegram {

    private String tokenAccess;
    private String  chatId;
    private String  userName;


    public DataUserTelegram() {
    }

    public DataUserTelegram(String tokenAccess, String chatId, String userName) {
        this.tokenAccess = tokenAccess;
        this.chatId = chatId;
        this.userName = userName;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
