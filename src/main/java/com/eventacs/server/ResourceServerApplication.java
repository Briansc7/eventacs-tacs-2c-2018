package com.eventacs.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class ResourceServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(ResourceServerApplication.class, args);
        MongoContext.init();
    }

}