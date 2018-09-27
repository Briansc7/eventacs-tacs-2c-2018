package com.eventacs.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Esta clase es la encargada de iniciar la aplicación
 * @author facundofigoli
 */


@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class, args);
    }
}