package com.eventacs.user.repository;

import com.eventacs.user.model.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UsersRepoImpl implements UsersRepo {

    private RedisTemplate<String,User> redisTemplate;
    private HashOperations  hashOperations;

    public UsersRepoImpl(RedisTemplate<String,User> redisTemplate) {
        this.redisTemplate = redisTemplate;

        hashOperations=redisTemplate.opsForHash();

    }

    @Override
    public Map<String,User> findAll() {
        return hashOperations.entries("USER");

    }

    @Override
    public User findByChatId(String chatId) {
        return (User)hashOperations.get("USER",chatId);
    }

    @Override
    public void update(User user) {
        hashOperations.put("USER",user.getChatId(),user);
    }

    @Override
    public void delete(String tokenId) {
    }

    @Override
    public void save(User user) {
        hashOperations.put("USER",user.getChatId(),user);

    }
}
