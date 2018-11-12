package com.eventacs.external.telegram.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class MainTelegram {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTelegram.class);

    @Autowired
    private TacsBot tacsBot;

    public MainTelegram(TacsBot tacsBot) {
        this.tacsBot = tacsBot;
        initBot();
    }

    private void initBot() {
        // Se crea un nuevo Bot API
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

//        try {
//            // Se registra el bot
//            telegramBotsApi.registerBot(this.tacsBot);
//            LOGGER.info("BOT REGISTRADO");
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

    }

}

