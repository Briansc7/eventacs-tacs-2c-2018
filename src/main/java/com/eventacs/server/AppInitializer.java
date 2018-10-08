package com.eventacs.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * Esta clase es la encargada de iniciar la aplicación
 * @author facundofigoli
 */


@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(AppInitializer.class, args);
    }
}