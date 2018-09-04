package com.eventacs.account.service;

import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.user.dto.UserInfoDTO;

import java.util.ArrayList;

public class AccountService {

    // TODO Cambiarle el nombre de newUserDTO
    public UserInfoDTO createUser(UserAccountDTO userAccountDTO) {
        return new UserInfoDTO("testid", userAccountDTO.getName(), userAccountDTO.getLastName(), new ArrayList<>());
    }

    public UserInfoDTO login(String name, String encryptedPassword) {
        return new UserInfoDTO("testid", name,  "lastName", new ArrayList<>());
    }

    public UserInfoDTO logout(String sessionCookieId) {
        return new UserInfoDTO("testid", "testname",  "testLastName", new ArrayList<>());
    }

}
