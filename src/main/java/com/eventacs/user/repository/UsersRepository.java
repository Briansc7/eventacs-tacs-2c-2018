package com.eventacs.user.repository;

import com.eventacs.external.telegram.client.JdbcDao.JdbcDaoUserData;
import com.eventacs.user.dto.UserDataDTO;
import com.eventacs.user.model.User;
import java.util.List;
import java.util.Optional;

public class UsersRepository {

    private JdbcDaoUserData userData;

    public UsersRepository(JdbcDaoUserData userData) {
        this.userData = userData;
    }

    private List<User> users;

    public UserDataDTO getUserDataByUserId(String userId) {
        Object obj = userData.obtainUserDataFromDB(userId);
        return obj != null ? (UserDataDTO) obj : null;
    }

    public List<User> getUsers() {
        return this.users;
    }

}