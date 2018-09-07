package com.eventacs.external.telegram.client;

import com.eventacs.server.AppInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TacsBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    @Override
    public void onUpdateReceived(final Update update) {
        // Esta función se invocará cuando nuestro bot reciba un mensaje

        LOGGER.info("MENSAJE  RECIIDO"+update);

        // Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();

        LOGGER.info("MENSAJE "+messageTextReceived);
        // Se obtiene el id de chat del usuario
        final long chatId = update.getMessage().getChatId();

        // Se crea un objeto mensaje
        SendMessage message = new SendMessage().setChatId(chatId).setText(messageTextReceived);

        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // Se devuelve el nombre que dimos al bot al crearlo con el BotFather
        return "TacsBot";
    }

    @Override
    public String getBotToken() {
        // Se devuelve el token que nos generó el BotFather de nuestro bot
        return "696368973:AAHhYOg8QAs5ytQO96_VhQue7k75h3f7rO4";
    }
}