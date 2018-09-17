package com.eventacs.user.repository;

import com.eventacs.user.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepository {

    private List<UserInfoDTO> users;

    public UsersRepository() {
        this.users = new ArrayList<>();
        this.users.add(new UserInfoDTO("id1", "name", "lastName", new ArrayList<>()));
        this.users.add(new UserInfoDTO("id2", "name", "lastName", new ArrayList<>()));
    }

    public Optional<UserInfoDTO> getByUserId(String userId) {
        return this.users.stream().filter(user -> user.getId().equals(userId)).findFirst();
    }

    public List<UserInfoDTO> getUsers() {
        return this.users;
    }

}
