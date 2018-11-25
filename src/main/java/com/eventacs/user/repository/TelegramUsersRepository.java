package com.eventacs.user.repository;
import com.eventacs.external.eventbrite.model.GetAccessToken;

import java.util.Map;

public interface TelegramUsersRepository {

    Map<Long,GetAccessToken> findAll();
    String findByChatId(long chatId);
    String findUserIdByChatId(long chatId);
    void update(final long chatId, final GetAccessToken accessToken);
    void save(final long chatId, final GetAccessToken accessToken);
}
