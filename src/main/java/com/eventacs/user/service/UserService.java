package com.eventacs.user.service;

import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public UserInfoDTO getUser(String userId) {
        return new UserInfoDTO(userId, "testname", "lastName", new ArrayList<>());
    }

    public List<UserInfoDTO> getUsers() {

        List<UserInfoDTO> users = new ArrayList<>();
        users.add(new UserInfoDTO("testname1", "testpassword1", "lastName", new ArrayList<>()));
        users.add(new UserInfoDTO("testname2", "testpassword2", "lastName", new ArrayList<>()));

        return users;

    }

    public AlarmDTO createAlarm(String userId, SearchDTO searchDTO) {
        return new AlarmDTO("testid", userId, searchDTO);
    }

}
