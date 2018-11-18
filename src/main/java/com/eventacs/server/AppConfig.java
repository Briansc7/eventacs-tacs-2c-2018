package com.eventacs.server;

import com.eventacs.account.service.AccountService;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.external.eventbrite.mapping.CategoryMapper;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.external.eventbrite.mapping.PaginationMapper;
import com.eventacs.external.telegram.client.MainTelegram;
import com.eventacs.external.telegram.client.TacsBot;
import com.eventacs.httpclient.RestClient;
import com.eventacs.mongo.EventacsMongoClient;
import com.eventacs.user.mapping.AlarmsMapper;
import com.eventacs.user.mapping.EventListsMapper;
import com.eventacs.user.mapping.UsersMapper;
import com.eventacs.user.model.User;
import com.eventacs.user.repository.AlarmsRepository;
import com.eventacs.user.repository.TelegramUsersRepository;
import com.eventacs.user.repository.TelegramUsersRepositoryImpl;
import com.eventacs.user.repository.UsersRepository;
import com.eventacs.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.eventacs")
public class AppConfig {

    @Bean
    public AccountService accountService() { return new AccountService(); }

    @Bean
    public UserService userService() { return new UserService(usersRepository(), usersMapper(), alarmsRepository(), alarmsMapper(), eventListsMapper()); }

    @Bean
    public EventListsMapper eventListsMapper() {return new EventListsMapper(); }

    @Bean
    public AlarmsMapper alarmsMapper() { return new AlarmsMapper(); }

    @Bean
    public UsersMapper usersMapper() { return new UsersMapper(); }

    @Bean
    public AlarmsRepository alarmsRepository() { return new AlarmsRepository(); }

    @Bean
    public UsersRepository usersRepository() {
        return new UsersRepository();
    }

    @Bean
    public EventService eventService() {
        return new EventService(eventbriteFacade());
    }

    @Bean
    public EventbriteFacade eventbriteFacade() {
        return new EventbriteFacade(eventbriteClient(), eventMapper(), categoryMapper(), paginationMapper());
    }

    @Bean
    public EventbriteClient eventbriteClient() {
        return new EventbriteClient(restClient());
    }

    @Bean
    public EventMapper eventMapper() {
        return new EventMapper();
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }

    @Bean
    public PaginationMapper paginationMapper() { return new PaginationMapper(); }

    @Bean
    public RestClient restClient() {
        return new RestClient();
    }

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<Long, String> redisTemplate(){
        RedisTemplate<Long,String> template = new RedisTemplate<Long,String>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }
    /*
    @Repository
    public interface UsersRepo extends CrudRepository<Long,String>  {
    }*/

    @Bean
    public TelegramUsersRepositoryImpl telegramUsersRepositoryImpl() {
        return new TelegramUsersRepositoryImpl(redisTemplate());
    }

    @Bean
    public TacsBot tacsBot() { return new TacsBot(eventService(), telegramUsersRepositoryImpl()); }

    @Bean
    public MainTelegram mainTelegram() { return new MainTelegram(tacsBot()); }

    @Bean
    public EventListRepository eventListRepository(EventacsMongoClient eventacsMongoClient) { return new EventListRepository(eventacsMongoClient); }

}