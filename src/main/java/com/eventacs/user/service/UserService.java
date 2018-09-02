package com.eventacs.user.service;

import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public UserInfoDTO getUser(String userId) {
        return new UserInfoDTO(userId, "testname");
    }

    public List<UserInfoDTO> getUsers() {

        List<UserInfoDTO> users = new ArrayList<>();
        users.add(new UserInfoDTO("testname1", "testpassword1"));
        users.add(new UserInfoDTO("testname2", "testpassword2"));

        return users;

    }

    public AlarmDTO createAlarm(String userId, SearchDTO searchDTO) {
        return new AlarmDTO("testid", userId, searchDTO);
    }

}
