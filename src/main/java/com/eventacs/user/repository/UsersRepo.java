package com.eventacs.user.repository;
import com.eventacs.user.model.User;

import java.util.List;
import java.util.Map;

public interface UsersRepo {

    Map<String,User> findAll();
    User findByChatId(String chatId);
    void update(User user);
    void delete(String tokenId):
    void save(User user);
}
