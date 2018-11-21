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
            return (String)hashOperations.get("CHATID",chatId);
        }
        catch(Exception e){
            return jdbcDaoTelegramUserData.getDataByChatId(Long.toString(chatId)).get(0).getTokenAccess();
        }
    }
    @Override
    public String findUserIdByChatId(final long chatId) {
        //try{
        //    return (String)hashOperations.get("CHATID",chatId);
        //}
        //catch(Exception e){
            return jdbcDaoTelegramUserData.getDataByChatId(Long.toString(chatId)).get(0).getUserName();
        //}
    }

    @Override
    public void update(final long chatId, final String accessToken) {
        hashOperations.put("CHATID",chatId,accessToken);
    }

    @Override
    public void save(final long chatId, final GetAccessToken accessToken) {
        try{
            hashOperations.put("CHATID",chatId,accessToken.getAccess_token());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        jdbcDaoTelegramUserData.insertTokenChatId(accessToken, Long.toString(chatId));
    }
}