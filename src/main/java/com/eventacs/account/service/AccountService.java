package com.eventacs.account.service;

import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.account.dto.UserLoginDTO;
import com.eventacs.external.telegram.client.JdbcDao.JdbcDaoTelegramUserData;
import com.eventacs.user.dto.UserInfoDTO;

import java.util.ArrayList;

public class AccountService {

    private JdbcDaoTelegramUserData userData;

    public AccountService(JdbcDaoTelegramUserData userData) {
        this.userData = userData;
    }

    // TODO Cambiarle el nombre de newUserDTO
    public boolean createUser(UserAccountDTO userAccountDTO) {
        userData.insertUserDataAccount(userAccountDTO);
        return true;
    }

    public UserInfoDTO login(UserLoginDTO userLoginDTO) {
        return new UserInfoDTO("testid", userLoginDTO.getName(),  "lastName", new ArrayList<>());
    }

    public UserInfoDTO logout(String sessionCookieId) {
        return new UserInfoDTO("testid", "testname",  "testLastName", new ArrayList<>());
    }

}
