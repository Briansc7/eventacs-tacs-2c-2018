package com.eventacs.server;

import com.eventacs.account.service.AccountService;
import com.eventacs.external.eventbrite.mapping.CategoryMapper;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.external.telegram.client.MainTelegram;
import com.eventacs.external.telegram.client.TacsBot;
import com.eventacs.httpclient.RestClient;
import com.eventacs.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.eventacs")
public class AppConfig {

    @Bean
    public AccountService accountService() { return new AccountService(); }

    @Bean
    public UserService userService() { return new UserService(); }

    @Bean
    public EventService eventService() { return new EventService(eventbriteFacade()); }

    @Bean
    public EventbriteFacade eventbriteFacade() { return new EventbriteFacade(eventbriteClient(), eventMapper(), categoryMapper()); }

    @Bean
    public EventbriteClient eventbriteClient() { return new EventbriteClient(restClient()); }

    @Bean
    public EventMapper eventMapper() { return new EventMapper(); }

    @Bean
    public CategoryMapper categoryMapper() { return new CategoryMapper(); }

    @Bean
    public RestClient restClient() { return new RestClient(); }

    @Bean
    public TacsBot tacsBot() { return new TacsBot(eventService()); }

    @Bean
    public MainTelegram mainTelegram() { return new MainTelegram(tacsBot()); }

}