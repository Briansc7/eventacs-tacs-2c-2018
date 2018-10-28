package com.eventacs.external.telegram.client;

import com.eventacs.event.model.Category;
import com.eventacs.event.model.Event;
import com.eventacs.event.service.EventService;
import com.eventacs.server.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.eventacs.external.telegram.client.estados.*;

enum estados{
    inicio, agregarevento, revisareventos, buscarevento, login
}

@Component
public class TacsBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);

    private static HashMap<Long, estados> chatStates = new HashMap<Long, estados>();

    private static HashMap<Long, String> usuarios = new HashMap<Long, String>();

    ComandoAyuda comandoAyuda = new ComandoAyuda();
    ComandoAgregarEvento comandoAgregarEvento = new ComandoAgregarEvento();
    ComandoRevisarEventos comandoRevisarEventos = new ComandoRevisarEventos();
    ComandoBuscarEvento comandoBuscarEvento = new ComandoBuscarEvento();
    ComandoLogin comandoLogin = new ComandoLogin();

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


        LOGGER.info("Mensaje completo recibido: " + update);

        // Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();

        LOGGER.info("Texto recibido: " + messageTextReceived);
        // Se obtiene el id de chat del usuario
        final long chatId = update.getMessage().getChatId();

        LOGGER.info("ID de chat: " + chatId);

        if(!chatStates.containsKey(chatId)){
            chatStates.put(chatId, inicio);
        }

        LOGGER.info("Estado: " + chatStates.get(chatId));

        Optional<String> keyword = Optional.empty();
        Optional<List<String>> categories = Optional.empty();
        Optional<LocalDate> startDate = Optional.of(LocalDate.now());
        Optional<LocalDate> endDate = Optional.of(LocalDate.now().plusDays(1));
        List<Event> listaEventos;

        // Se crea un objeto mensaje

        LOGGER.info("Nombre de Usuario: " + update.getMessage().getFrom().getFirstName());



        //String mensajeAEnviar = "";
        StringBuilder mensajeAEnviar = new StringBuilder ();

        String[] parts = messageTextReceived.split(" ");


        switch (chatStates.get(chatId)){
            case inicio:
                mostrar_mensaje_inicial(parts, mensajeAEnviar, update, chatStates, this);
                break;
            case agregarevento:
                comandoAgregarEvento.agregarEvento(parts, chatStates, chatId, this);
                break;
            case revisareventos:
                comandoRevisarEventos.revisarEventos(parts, chatStates, chatId, this);
                break;
            case buscarevento:
                comandoBuscarEvento.buscarEventos(parts, chatStates, chatId, this);
                break;
            case login:
                comandoLogin.login(parts, chatStates, chatId, this);
                break;
            default:
                break;

        }

    }

    private void mostrar_mensaje_inicial(String[] parts, StringBuilder mensajeAEnviar, Update update, HashMap<Long, estados> chatStates, TacsBot tacsBot) {

        final long chatId = update.getMessage().getChatId();

        switch (parts[0]) {
            case "/start":
                String nombreUsuario = update.getMessage().getFrom().getFirstName();
                mensajeAEnviar.append("Bienvenido ").append(nombreUsuario).append("\n\n");
            case "/ayuda":
                comandoAyuda.mostrarAyuda(parts, chatStates, chatId, this);
                break;
            case "/agregarevento":
                TacsBot.chatStates.put(chatId, agregarevento);
                comandoAgregarEvento.agregarEvento(parts, chatStates, chatId, this);
                break;
            case "/revisareventos":
                TacsBot.chatStates.put(chatId, revisareventos);
                comandoRevisarEventos.revisarEventos(parts, chatStates, chatId, this);
                break;
            case "/buscarevento":
                TacsBot.chatStates.put(chatId, buscarevento);
                comandoBuscarEvento.buscarEventos(parts, chatStates, chatId, this);
                break;
            case "/login":
                TacsBot.chatStates.put(chatId, login);
                comandoLogin.login(parts, chatStates, chatId, this);
                break;
            default:
                mensajeAEnviar.append("opción no válida");
                break;
        }
    }

    private StringBuilder getIdNombreEventosEncontrados(List<Event> listaEventos, StringBuilder mensajeAEnviar) {
        listaEventos = listaEventos.size() > 40 ? listaEventos.subList(0, 40):listaEventos.subList(0, listaEventos.size());//me quedo con los primeros 10. Luego se va a implementar paginación
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

    public void enviarMensaje(StringBuilder mensajeAEnviar, long chatId){
        SendMessage message = new SendMessage().setChatId(chatId).setText(mensajeAEnviar.toString());
        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void agregarEvento(String idLista, String idEvento, long chatId){
        String userID = getUserId(chatId); //se identifica al usuario a partir del chatid
        this.eventService.addEvent(idLista, idEvento, userID);
    }

    public void revisarEventos(String idLista, long chatId){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        String userid = getUserId(chatId);
        List<Event> listaEventos = this.eventService.getEventList(idLista, userid).getEvents();

        if(listaEventos.isEmpty()){
            mensajeAEnviar.append("No se encontraron eventos");
        }
        else{
            mensajeAEnviar = getIdNombreEventosEncontrados(listaEventos, mensajeAEnviar);
        }

        enviarMensaje(mensajeAEnviar, chatId);
    }

    public StringBuilder buscarEventos(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate,Optional<BigInteger> page){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        List<Event> listaEventos = this.eventService.getEvents(keyword, categories, startDate, endDate, page);

        if(listaEventos.isEmpty()){
            mensajeAEnviar.append("No se encontraron eventos");
        }
        else{
            mensajeAEnviar = getIdNombreEventosEncontrados(listaEventos, mensajeAEnviar);
        }

        return mensajeAEnviar;
    }

    public static void guardarToken(final long key, final String value) {
        () -> AppConfig.TelegramRepository
    }

    public static void guardarCuentaTelegram(long chatId, String username) {
        usuarios.put(chatId, username);
    }

    public String getUserId(long chatId){
        return usuarios.get(chatId);
        //return "id1";
    }

    public boolean existeUserConChatID(long chatId) {
        return usuarios.containsKey(chatId);
    }

    public StringBuilder categoriasDisponibles(){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        List<Category> listaCategorias = this.eventService.getCategories();

        if(listaCategorias.isEmpty()){
            mensajeAEnviar.append("No se encontraron categorias");
        }
        else{
            mensajeAEnviar = getIdNombreCategoriasEncontradas(listaCategorias, mensajeAEnviar);
        }

        return mensajeAEnviar;
    }

    private StringBuilder getIdNombreCategoriasEncontradas(List<Category> listaCategorias, StringBuilder mensajeAEnviar) {
        mensajeAEnviar.append("Categorías disponibles:\n");
        StringBuilder finalMensajeAEnviar = mensajeAEnviar;
        listaCategorias.forEach(e -> agregarCategoria(e, finalMensajeAEnviar));
        mensajeAEnviar = finalMensajeAEnviar;
        return mensajeAEnviar;
    }

    private void agregarCategoria(Category e, StringBuilder mensajeAEnviar) {
        mensajeAEnviar.append("ID: ").append(e.getId()).append("\n");
        mensajeAEnviar.append("Nombre: ").append(e.getName()).append("\n\n");
    }
}