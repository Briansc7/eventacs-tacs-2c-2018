package com.eventacs.external.telegram.client;

import com.eventacs.event.model.Event;
import com.eventacs.event.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


        LOGGER.info("MENSAJE  RECIIDO" + update);

        // Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();

        LOGGER.info("MENSAJE " + messageTextReceived);
        // Se obtiene el id de chat del usuario
        final long chatId = update.getMessage().getChatId();

        Optional<String> keyword = Optional.empty();
        Optional<List<String>> categories = Optional.empty();
        Optional<LocalDateTime> startDate = Optional.of(LocalDateTime.now());
        Optional<LocalDateTime> endDate = Optional.of(LocalDateTime.now().plusDays(1));
        List<Event> listaEventos;

        // Se crea un objeto mensaje

        LOGGER.info("Contenido " + update.getMessage().getFrom().getFirstName());

        String nombreUsuario = update.getMessage().getFrom().getFirstName();

        //String mensajeAEnviar = "";
        StringBuilder mensajeAEnviar = new StringBuilder ();

        String[] parts = messageTextReceived.split(" ");

        switch (parts[0]) {
            case "/start":
                mensajeAEnviar.append("Bienvenido ").append(nombreUsuario).append("\n");
            case "/ayuda":
                mensajeAEnviar.append("Buscar eventos con /buscarevento keyword IdCategoria fechaYhoraInicio fechaYhoraFin\n");
                mensajeAEnviar.append("Ej.: /buscarevento party 105 2018-09-18T00:00:00 2018-09-19T00:00:00\n\n");
                mensajeAEnviar.append("Agregar eventos a una lista de eventos con /agregarevento IdLista IdEvento\n");
                mensajeAEnviar.append("Ej.: /agregarevento 1 50399583511\n\n");
                mensajeAEnviar.append("Ver eventos de una lista de eventos con /revisareventos IdLista\n");
                mensajeAEnviar.append("Ej.: /revisareventos 1\n\n");
                break;
            case "/buscarevento":
                SendMessage message = new SendMessage().setChatId(chatId).setText("Procesando...");
                try {
                    // Se envía el mensaje
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                switch (parts.length){
                    default:
                        mensajeAEnviar.append("Cantidad de argumentos inválido\n");
                        mensajeAEnviar.append("Ejemplo de uso: /buscarevento party 105 2018-09-18T00:00:00 2018-09-19T00:00:00");
                        break;
                    case 5:
                        endDate = Optional.of(LocalDateTime.parse(parts[4]));
                    case 4:
                        startDate = Optional.of(LocalDateTime.parse(parts[3]));
                    case 3:
                        categories = Optional.of(new ArrayList<>());
                        categories.map(c -> c.add(parts[2])); //105 es Música
                    case 2:
                        keyword = Optional.of(parts[1]);
                        listaEventos = this.eventService.getEvents(keyword, categories, startDate, endDate);
                        if(listaEventos.isEmpty()){
                            mensajeAEnviar.append("No se encontraron eventos");
                        }
                        else{
                            mensajeAEnviar = getIdNombreEventosEncontrados(listaEventos, mensajeAEnviar);
                        }
                        break;
                    case 1:
                        mensajeAEnviar.append("Debe agregar los argumentos de búsqueda en el comando");
                        break;
                }
                break;
            case "/agregarevento":
                this.eventService.addEvent(parts[1], parts[2]);
                mensajeAEnviar.append("Evento Agregado");
                break;
            case "/revisareventos":
                //listaEventos = this.eventService.getEventsFromList(parts[1]);
                mensajeAEnviar = getIdNombreEventosEncontrados(listaEventos, mensajeAEnviar);
                mensajeAEnviar.append("Ingrese la lista de eventos");
                break;
            default:
                mensajeAEnviar.append("opción no válida");
                break;
        }

        SendMessage message = new SendMessage().setChatId(chatId).setText(mensajeAEnviar.toString());

        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder getIdNombreEventosEncontrados(List<Event> listaEventos, StringBuilder mensajeAEnviar) {
        listaEventos = listaEventos.subList(0, 10);//me quedo con los primeros 10. Luego se va a implementar paginación
        mensajeAEnviar.append("Eventos encontrados:\n");
        StringBuilder finalMensajeAEnviar = mensajeAEnviar;
        listaEventos.forEach(e -> agregarDatosEvento(e, finalMensajeAEnviar));
        mensajeAEnviar = finalMensajeAEnviar;
        return mensajeAEnviar;
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

    public void agregarDatosEvento(Event e, StringBuilder mensajeAEnviar) {
        mensajeAEnviar.append("ID: ").append(e.getId()).append("\n");
        mensajeAEnviar.append("Nombre: ").append(e.getName()).append("\n\n");
    }
}