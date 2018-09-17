package com.eventacs.external.telegram.client;

import com.eventacs.event.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class TacsBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);

    @Autowired
    private EventService eventService;
    private int numero = 0;

    public TacsBot(EventService eventService) {
        this.eventService = eventService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        // Esta función se invocará cuando nuestro bot reciba un mensaje

        Thread mythread;

        LOGGER.info("MENSAJE  RECIIDO" + update);

        // Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();

        LOGGER.info("MENSAJE " + messageTextReceived);
        // Se obtiene el id de chat del usuario
        final long chatId = update.getMessage().getChatId();
/*
        Optional<String> keyword = Optional.of("party");
        Optional<List<String>> categories = Optional.of(new ArrayList<String>());
        categories.map(c -> c.add("Music"));
        Optional<LocalDate> startDate = Optional.of(LocalDate.now().minusDays(7));
        Optional<LocalDate> endDate = Optional.of(LocalDate.now());

        List<Event> listaEventos = this.eventService.getEvents(keyword, categories, startDate, endDate);

        String primerEventoNombre = listaEventos.get(1).getName();

        // Se crea un objeto mensaje*/

        LOGGER.info("Contenido " + update.getMessage().getFrom().getFirstName());

        String nombreUsuario = update.getMessage().getFrom().getFirstName();

        String mensajeAEnviar = "";

        String[] parts = messageTextReceived.split(" ");

        switch (parts[0]) {
            case "/start":
                mensajeAEnviar = "Bienvenido " + nombreUsuario;
                break;
            case "/buscar-evento":
                mensajeAEnviar += "Keyword= " + parts[1];
                mensajeAEnviar += " Categoria= " + parts[2];
                mensajeAEnviar += " Fecha Inicio= " + parts[3];
                mensajeAEnviar += " Fecha Fin= " + parts[4];
                break;
            case "/agregar-evento":
                mensajeAEnviar = "Ingrese el evento a agregar";
                break;
            case "/revisar-eventos":
                mensajeAEnviar = "Ingrese la lista de eventos";
                break;
            default:
                mensajeAEnviar = "opción no válida";
                break;
        }

        SendMessage message = new SendMessage().setChatId(chatId).setText(mensajeAEnviar);

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