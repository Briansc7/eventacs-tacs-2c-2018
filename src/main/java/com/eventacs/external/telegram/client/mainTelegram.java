package com.eventacs.external.telegram.client;

import com.eventacs.server.AppInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class mainTelegram {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    public static void algo(String[] args) {
        // Se inicializa el contexto de la API
        ApiContextInitializer.init();

        // Se crea un nuevo Bot API
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            // Se registra el bot
            telegramBotsApi.registerBot(new TacsBot());
            LOGGER.info("BOT REGISTRADO");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

