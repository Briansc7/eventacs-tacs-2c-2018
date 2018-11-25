package com.eventacs.server;

import com.eventacs.account.service.AccountService;
import com.eventacs.event.dto.EventListMapper;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.external.eventbrite.mapping.CategoryMapper;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.external.eventbrite.mapping.PaginationMapper;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.JdbcDao.JdbcDaoTelegramUserData;
import com.eventacs.external.telegram.client.MainTelegram;
import com.eventacs.external.telegram.client.TacsBot;
import com.eventacs.httpclient.RestClient;
import com.eventacs.mongo.EventacsMongoClient;
import com.eventacs.user.mapping.AlarmsMapper;
import com.eventacs.user.mapping.EventListsMapper;
import com.eventacs.user.mapping.UsersMapper;
import com.eventacs.event.repository.AlarmsRepository;
import com.eventacs.user.repository.TelegramUsersRepositoryImpl;
import com.eventacs.user.repository.UsersRepository;
import com.eventacs.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Repository;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
@PropertySource({ "classpath:persistence.properties" })
@ComponentScan(basePackages = "com.eventacs")
public class AppConfig {

    @Autowired
    private Environment env;

    @Value("classpath:schema.sql")
    private Resource schemaScript;

    @Bean
    public AccountService accountService() { return new AccountService(jdbcDaoTelegramUserData()); }

    @Bean
    public UserService userService() { return new UserService(usersRepository(), usersMapper(), alarmsRepository(eventacsMongoClient()), alarmsMapper(), eventListsMapper()); }

    @Bean
    public EventListsMapper eventListsMapper() {return new EventListsMapper(); }

    @Bean
    public AlarmsMapper alarmsMapper() { return new AlarmsMapper(); }

    @Bean
    public UsersMapper usersMapper() { return new UsersMapper(); }

    @Bean
    public AlarmsRepository alarmsRepository(EventacsMongoClient eventacsMongoClient) { return new AlarmsRepository(eventacsMongoClient); }

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
    JdbcDaoTelegramUserData jdbcDaoTelegramUserData() { return new JdbcDaoTelegramUserData(dataSource()); };

    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<Long, GetAccessToken> redisTemplate(){
        RedisTemplate<Long,GetAccessToken> template = new RedisTemplate<Long,GetAccessToken>();
        template.setConnectionFactory(jedisConnectionFactory());
        //template.setValueSerializer(new LdapFailAwareRedisObjectSerializer());
        //template.setKeySerializer(new LongRedisSerializer());
        //template.setHashKeySerializer(new LongRedisSerializer());
        template.setHashValueSerializer(new LdapFailAwareRedisObjectSerializer());
        return template;
    }
    /*
    @Repository
    public interface UsersRepo extends CrudRepository<Long,String>  {
    }*/

    @Bean
    public TelegramUsersRepositoryImpl telegramUsersRepositoryImpl() {
        return new TelegramUsersRepositoryImpl(redisTemplate(), jdbcDaoTelegramUserData());
    }

    @Bean
    public TacsBot tacsBot() { return new TacsBot(eventService(), telegramUsersRepositoryImpl()); }

    @Bean
    public MainTelegram mainTelegram() { return new MainTelegram(tacsBot()); }

    @Bean
    public EventListRepository eventListRepository(EventacsMongoClient eventacsMongoClient, EventListMapper eventListMapper) { return new EventListRepository(eventacsMongoClient, eventListMapper); }

    @Bean
    public EventListMapper eventListMapper(){return new EventListMapper();}

    @Bean
    public EventacsMongoClient eventacsMongoClient() { return new EventacsMongoClient(); }

    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

}