package com.eventacs.account.service;

import com.eventacs.account.dto.UserAccountDTO;
import com.eventacs.user.dto.UserInfoDTO;

public class AccountService {

    // TODO Cambiarle el nombre de newUserDTO
    public UserInfoDTO createUser(UserAccountDTO userAccountDTO) {
        return new UserInfoDTO("testid", userAccountDTO.getName());
    }

    public UserInfoDTO login(UserAccountDTO userAccountDTO) {
        return new UserInfoDTO("testid", userAccountDTO.getName());
    }

    public UserInfoDTO logout(String sessionCookieId) {
        return new UserInfoDTO("testid", "testname");
    }

}
