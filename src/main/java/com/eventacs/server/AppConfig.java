package com.eventacs.server;

import com.eventacs.account.service.AccountService;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
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
    public AccountService accountService() {
        return new AccountService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public EventService eventService() { return new EventService(eventbriteFacade()); }

    @Bean
    public EventbriteFacade eventbriteFacade() { return new EventbriteFacade(eventbriteClient(), eventMapper()); }

    @Bean
    public EventbriteClient eventbriteClient() { return new EventbriteClient(restClient()); }

    @Bean
    public RestClient restClient() { return new RestClient(); }

    @Bean
    public EventMapper eventMapper() { return new EventMapper(); }

}