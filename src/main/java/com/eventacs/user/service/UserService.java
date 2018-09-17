package com.eventacs.user.service;

import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UsersRepository repository;

    public UserService(UsersRepository repository) {
        this.repository = repository;
    }

    public Optional<UserInfoDTO> getUser(String userId) {
        return this.repository.getByUserId(userId);
    }

    public List<UserInfoDTO> getUsers() {
        return this.repository.getUsers();
    }

    public AlarmDTO createAlarm(String userId, SearchDTO searchDTO) {
        return new AlarmDTO("testid", userId, searchDTO);
    }

    public UsersRepository getRepository() {
        return repository;
    }

    public void setRepository(UsersRepository repository) {
        this.repository = repository;
    }

}
