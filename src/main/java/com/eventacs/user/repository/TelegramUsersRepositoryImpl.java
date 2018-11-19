package com.eventacs.user.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class TelegramUsersRepositoryImpl implements TelegramUsersRepository {

    private RedisTemplate<Long,String> redisTemplate;
    private HashOperations  hashOperations;

    public TelegramUsersRepositoryImpl(RedisTemplate<Long,String> redisTemplate) {
        this.redisTemplate = redisTemplate;
     //   hashOperations=redisTemplate.opsForHash();
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<Long,String> findAll() {
        return hashOperations.entries("CHATID");
    }

    @Override
    public String findByChatId(final long chatId) {
        return (String)hashOperations.get("CHATID",chatId);
    }

    @Override
    public void update(final long chatId, final String accessToken) {
        hashOperations.put("CHATID",chatId,accessToken);
    }

    @Override
    public void save(final long chatId, final String accessToken) {
        hashOperations.put("CHATID",chatId,accessToken);
    }
}