package com.eventacs.external.telegram.client;

import com.eventacs.server.AppInitializer;
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
    private static TacsBot tacsbot;

    public MainTelegram(TacsBot tacsbot) {
        MainTelegram.tacsbot = tacsbot;
        inicializarBot();

    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public TacsBot getTacsbot() {
        return tacsbot;
    }

    public void setTacsbot(TacsBot tacsbot) {
        this.tacsbot = tacsbot;
    }

    public MainTelegram() {
    }


    public static void inicializarBot() {
        // Se inicializa el contexto de la API
        ApiContextInitializer.init();

        // Se crea un nuevo Bot API
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            // Se registra el bot
            telegramBotsApi.registerBot(tacsbot);
            LOGGER.info("BOT REGISTRADO");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

