package com.eventacs.account.service;

import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.account.dto.UserLoginDTO;
import com.eventacs.external.telegram.client.JdbcDao.JdbcDaoUserData;
import com.eventacs.user.dto.UserInfoDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

public class AccountService {

    private JdbcDaoUserData userData;

    public AccountService(JdbcDaoUserData userData) {
        this.userData = userData;
    }

    // TODO Cambiarle el nombre de newUserDTO
    public boolean createUser(UserAccountDTO userAccountDTO) {
        boolean userCreated = true;
        userAccountDTO.setPassword((new BCryptPasswordEncoder()).encode(userAccountDTO.getPassword()));
        try {
            userData.insertUserDataAccount(userAccountDTO);
        } catch (Exception e){
            userCreated = false;
        }
        return userCreated;
    }

    public UserInfoDTO login(UserLoginDTO userLoginDTO) {
        return new UserInfoDTO("testid", userLoginDTO.getName(),  "lastName", new ArrayList<>());
    }

    public UserInfoDTO logout(String sessionCookieId) {
        return new UserInfoDTO("testid", "testname",  "testLastName", new ArrayList<>());
    }

}
