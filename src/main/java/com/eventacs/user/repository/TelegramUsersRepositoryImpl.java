package com.eventacs.user.repository;

import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.JdbcDao.JdbcDaoTelegramUserData;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class TelegramUsersRepositoryImpl implements TelegramUsersRepository {

    private RedisTemplate<Long,String> redisTemplate;
    private HashOperations  hashOperations;
    private JdbcDaoTelegramUserData jdbcDaoTelegramUserData;

    public TelegramUsersRepositoryImpl(RedisTemplate<Long,String> redisTemplate, JdbcDaoTelegramUserData jdbcDaoTelegramUserData) {
        this.redisTemplate = redisTemplate;
        this.jdbcDaoTelegramUserData = jdbcDaoTelegramUserData;
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
        try{
            String value =(String) hashOperations.get("CHATID",chatId);
            return value.substring(0,value.indexOf("/"));
        }
        catch(Exception e){
            //e.printStackTrace();
            return jdbcDaoTelegramUserData.getDataByChatId(Long.toString(chatId)).get(0).getTokenAccess();
        }
    }


    @Override
    public String findUserIdByChatId(final long chatId) {
        try{
            String value =(String) hashOperations.get("CHATID",chatId);
            return value.substring(value.indexOf("/")+1);
        }
        catch(Exception e){
            return jdbcDaoTelegramUserData.getDataByChatId(Long.toString(chatId)).get(0).getUserName();
        }
    }

    @Override
    public String findChatIdByUserId(final String userId) {
            return jdbcDaoTelegramUserData.getChatIdByUserId(userId).get(0).getChatId();
    }

    @Override
    public void update(final long chatId, final GetAccessToken accessToken) {
        String value = accessToken.getAccess_token()+"/"+accessToken.getUsername();
        hashOperations.put("CHATID",chatId,value);
    }

    @Override
    public void save(final long chatId, final GetAccessToken accessToken) {
        try{
            String value = accessToken.getAccess_token()+"/"+accessToken.getUsername();
            hashOperations.put("CHATID",chatId,value);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        jdbcDaoTelegramUserData.insertTokenChatId(accessToken, Long.toString(chatId));
    }
}