package com.eventacs.user.repository;
import com.eventacs.user.model.User;

import java.util.List;
import java.util.Map;

public interface TelegramUsersRepository {

    Map<Long,String> findAll();
    String findByChatId(String chatId);
    void update(final long chatId, final String accessToken);
    void save(final long chatId, final String accessToken);
}
