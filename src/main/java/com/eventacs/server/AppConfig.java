package com.eventacs.server;

import com.eventacs.account.service.AccountService;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.external.eventbrite.mapping.CategoryMapper;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.external.telegram.client.MainTelegram;
import com.eventacs.external.telegram.client.TacsBot;
import com.eventacs.httpclient.RestClient;
import com.eventacs.user.mapping.AlarmsMapper;
import com.eventacs.user.mapping.EventListsMapper;
import com.eventacs.user.mapping.UsersMapper;
import com.eventacs.user.repository.AlarmsRepository;
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
        return new EventbriteFacade(eventbriteClient(), eventMapper(), categoryMapper());
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

//    @Bean
//    public TacsBot tacsBot() { return new TacsBot(eventService()); }
//
//    @Bean
//    public MainTelegram mainTelegram() { return new MainTelegram(tacsBot()); }

}