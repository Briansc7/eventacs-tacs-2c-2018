package com.eventacs.external.telegram.client;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.model.*;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.httprequest.EventacsCommands;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.repository.TelegramUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.eventacs.external.telegram.client.estados.*;
import static java.lang.Math.toIntExact;

enum estados{
    inicio, agregarevento, revisareventos, buscarevento, login, crearlista, eliminarLista, cambiarnombrelista,
    crearalarma
}

@Component
public class TacsBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);

    private static HashMap<Long, estados> chatStates = new HashMap<Long, estados>();

    private static TelegramUsersRepository telegramUsersRepository;

    ComandoAyuda comandoAyuda = new ComandoAyuda();
    ComandoAgregarEvento comandoAgregarEvento = new ComandoAgregarEvento();
    ComandoRevisarEventos comandoRevisarEventos = new ComandoRevisarEventos();
    ComandoBuscarEvento comandoBuscarEvento = new ComandoBuscarEvento();
    ComandoLogin comandoLogin = new ComandoLogin();
    ComandoCrearLista comandoCrearLista = new ComandoCrearLista();
    ComandoEliminarLista comandoEliminarLista = new ComandoEliminarLista();
    ComandoCambiarNombreLista comandoCambiarNombreLista = new ComandoCambiarNombreLista();
    ComandoCrearAlarma comandoCrearAlarma = new ComandoCrearAlarma();

    @Autowired
    private EventService eventService;

    public TacsBot(EventService eventService, TelegramUsersRepository telegramUsersRepository) {
        this.eventService = eventService;
        this.telegramUsersRepository = telegramUsersRepository;
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

        if (update.hasCallbackQuery()){
            inlineButtonPresionado(update);
            return;
        }

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
                mostrar_mensaje_inicial(messageTextReceived, mensajeAEnviar, update, chatStates, this);
                break;
            case agregarevento:
                comandoAgregarEvento.agregarEvento(parts, chatStates, chatId, this);
                break;
            case revisareventos:
                comandoRevisarEventos.revisarEventos(parts, chatStates, chatId, this);
                break;
            case buscarevento:
                comandoBuscarEvento.buscarEventos(messageTextReceived, chatStates, chatId, this);
                break;
            case login:
                comandoLogin.login(parts, chatStates, chatId, this);
                break;
            case crearlista:
                comandoCrearLista.crearLista(messageTextReceived,chatStates,chatId, this);
                break;
            case eliminarLista:
                comandoEliminarLista.eliminarLista(parts, chatStates, chatId, this);
                break;
            case cambiarnombrelista:
                comandoCambiarNombreLista.cambiarNombreLista(messageTextReceived, chatStates, chatId, this);
                break;
            case crearalarma:
                comandoCrearAlarma.crearAlarma(messageTextReceived, chatStates, chatId, this);
                break;
            default:
                break;

        }

    }

    private void inlineButtonPresionado(Update update) {
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        if (call_data.equals("update_msg_text")) {
            String answer = "Updated message text";
            editarMensaje(message_id, chat_id, answer);
        }
    }

    private void editarMensaje(long message_id, long chat_id, String answer) {
        EditMessageText new_message = new EditMessageText()
                .setChatId(chat_id)
                .setMessageId(toIntExact(message_id))
                .setText(answer);
        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void mostrar_mensaje_inicial(String messageTextReceived, StringBuilder mensajeAEnviar, Update update, HashMap<Long, estados> chatStates, TacsBot tacsBot) {

        final long chatId = update.getMessage().getChatId();

        String[] parts = messageTextReceived.split(" ");

        switch (parts[0]) {
            case "/start":
                String nombreUsuario = update.getMessage().getFrom().getFirstName();
                mensajeAEnviar.append("Bienvenido ").append(nombreUsuario).append("\n\n");
                mensajeAEnviar.append("Elije uno de los siguientes comandos");
                enviarMensajeConTecladoComandos(mensajeAEnviar, chatId);
                break;
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
                comandoBuscarEvento.buscarEventos(messageTextReceived, chatStates, chatId, this);
                break;
            case "/login":
                TacsBot.chatStates.put(chatId, login);
                comandoLogin.login(parts, chatStates, chatId, this);
                break;
            case "/crearlista":
                TacsBot.chatStates.put(chatId, crearlista);
                comandoCrearLista.crearLista(messageTextReceived,chatStates,chatId, this);
                break;
            case "/eliminarlista":
                TacsBot.chatStates.put(chatId, eliminarLista);
                comandoEliminarLista.eliminarLista(parts, chatStates, chatId, this);
                break;
            case "/cambiarnombrelista":
                TacsBot.chatStates.put(chatId, cambiarnombrelista);
                comandoCambiarNombreLista.cambiarNombreLista(messageTextReceived, chatStates, chatId, this);
                break;
            case "/crearalarma":
                TacsBot.chatStates.put(chatId, crearalarma);
                comandoCrearAlarma.crearAlarma(messageTextReceived, chatStates, chatId, this);
                break;
            /*case "/test":
                mensajeAEnviar.append("Token: "+getAccessToken(chatId));
                mensajeAEnviar.append("\nUserId: "+getUserId(chatId));
                enviarMensaje(mensajeAEnviar, chatId);
                //enviarMensajeConBoton(mensajeAEnviar, chatId);
                //eventService.addEvent("1","50534572266",getUserId(chatId));
                //eventService.getEventList("2",getUserId(chatId));
                //eventService.changeListName("2","Nuevo nombre");
                eventService.deleteEventList("2");
                break;*/
            default:
                mensajeAEnviar.append("Comando no válido");
                enviarMensaje(mensajeAEnviar,chatId);
                comandoAyuda.mostrarAyuda(parts, chatStates, chatId, this);
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
        //return "TacsTestBot";
    }

    @Override
    public String getBotToken() {
        // Se devuelve el token que nos generó el BotFather de nuestro bot
        return "696368973:AAHhYOg8QAs5ytQO96_VhQue7k75h3f7rO4";
        //return "736121445:AAEGjBEwTBmjDFXSiQRw2Eox7Ry9Ulk9FXI";
    }

    public void agregarDatosEvento(Event e, StringBuilder mensajeAEnviar) {
        mensajeAEnviar.append("ID: /").append(e.getId()).append("\n");
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

    public void agregarEvento(String listId, String eventId, long chatId){
        EventacsCommands.addEventToList(getAccessToken(chatId), listId, eventId, getUserId(chatId));
//      this.eventService.addEvent(idLista, idEvento, userID);
    }

    public String getAccessToken(long chatId) {
        return telegramUsersRepository.findByChatId(chatId);
    }
    public void enviarMensajeRemoverTeclado(StringBuilder mensajeAEnviar, long chatId){
        SendMessage message = new SendMessage().setChatId(chatId).setText(mensajeAEnviar.toString());
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        message.setReplyMarkup(keyboardMarkup);
        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensajeConBoton(StringBuilder mensajeAEnviar, long chatId) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chatId)
                .setText("Enviaste /prueba");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Update message text").setCallbackData("update_msg_text"));
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void mostrarMenuComandos(long chatId){
        StringBuilder mensajeAEnviar = new StringBuilder();
        mensajeAEnviar.append("Elije uno de los siguientes comandos");
        enviarMensajeConTecladoComandos(mensajeAEnviar, chatId);

    }

    public void enviarMensajeConTecladoComandos(StringBuilder mensajeAEnviar, long chatId){


        KeyboardButton ayudaButton = new KeyboardButton();
        ayudaButton.setText("/ayuda");

        KeyboardButton buscareventoButton = new KeyboardButton();
        buscareventoButton.setText("/buscarevento");

        KeyboardButton revisareventosButton = new KeyboardButton();
        revisareventosButton.setText("/revisareventos");

        KeyboardButton agregareventoButton = new KeyboardButton();
        agregareventoButton.setText("/agregarevento");

        KeyboardButton crearlistaButton = new KeyboardButton();
        crearlistaButton.setText("/crearlista");

        KeyboardButton eliminarlistaButton = new KeyboardButton();
        eliminarlistaButton.setText("/eliminarlista");

        KeyboardButton cambiarnombrelistaButton = new KeyboardButton();
        cambiarnombrelistaButton.setText("/cambiarnombrelista");

        KeyboardButton crearalarmaButton = new KeyboardButton();
        crearalarmaButton.setText("/crearalarma");

        KeyboardButton loginButton = new KeyboardButton();
        loginButton.setText("/login");

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(ayudaButton);

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(buscareventoButton);
        keyboardRow2.add(crearalarmaButton);

        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(revisareventosButton);
        keyboardRow3.add(agregareventoButton);

        KeyboardRow keyboardRow4 = new KeyboardRow();
        keyboardRow4.add(crearlistaButton);
        keyboardRow4.add(eliminarlistaButton);
        keyboardRow4.add(cambiarnombrelistaButton);

        KeyboardRow keyboardRow5 = new KeyboardRow();
        keyboardRow5.add(loginButton);

        List<KeyboardRow> keyboardRowArrayList = new ArrayList<>();
        keyboardRowArrayList.add(keyboardRow1);
        keyboardRowArrayList.add(keyboardRow2);
        keyboardRowArrayList.add(keyboardRow3);
        keyboardRowArrayList.add(keyboardRow4);
        keyboardRowArrayList.add(keyboardRow5);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRowArrayList);
        replyKeyboardMarkup.setOneTimeKeyboard(true);//el teclado desaparece luego de elegir una opcion
        replyKeyboardMarkup.setResizeKeyboard(true);//hace el teclado mas responsive

        enviarMensajeConTeclado(mensajeAEnviar, chatId, replyKeyboardMarkup);


    }

    public void enviarMensajeConTeclado(StringBuilder mensajeAEnviar, long chatId, ReplyKeyboardMarkup teclado) {
        SendMessage message = new SendMessage().setChatId(chatId).setText(mensajeAEnviar.toString());
        message.setReplyMarkup(teclado);
        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar un mensaje removiendo el teclado en caso de que no sea de un uso
    public void enviarMensajeSinTeclado(StringBuilder mensajeAEnviar, long chatId) {
        SendMessage message = new SendMessage().setChatId(chatId).setText(mensajeAEnviar.toString());
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        message.setReplyMarkup(keyboardMarkup);
        try {
            // Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar un mensaje y mostrar un teclado con los botones "Si" y "No"
    public void enviarMensajeConOpcionSiNo(StringBuilder mensajeAEnviar, long chatId) {
        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText("Si");

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("No");

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton1);
        keyboardRow.add(keyboardButton2);

        List<KeyboardRow> keyboardRowArrayList = new ArrayList<>();
        keyboardRowArrayList.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRowArrayList);
        replyKeyboardMarkup.setOneTimeKeyboard(true);//el teclado desaparece luego de elegir una opcion
        replyKeyboardMarkup.setResizeKeyboard(true);//hace el teclado mas responsive


        enviarMensajeConTeclado(mensajeAEnviar, chatId, replyKeyboardMarkup);
    }


    public void revisarEventos(String idLista, long chatId){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        List<Event> listaEventos = EventacsCommands.getEventList(getAccessToken(chatId), idLista).getEvents();

        if(listaEventos.isEmpty()){
            mensajeAEnviar.append("No se encontraron eventos");
        }
        else{
            mensajeAEnviar = getIdNombreEventosEncontrados(listaEventos, mensajeAEnviar);
        }

        enviarMensaje(mensajeAEnviar, chatId);
    }

    public StringBuilder buscarEventos(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate,Optional<BigInteger> page, long chatId){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        EventsResponse eventsResponse = EventacsCommands.getEvents(getAccessToken(chatId), keyword, categories, startDate, endDate, page);
//        EventsResponse eventsResponse = this.eventService.getEvents(keyword, categories, startDate, endDate, page);

        if(eventsResponse.getEvents().isEmpty()){
            mensajeAEnviar.append("No se encontraron eventos");
        }
        else{
            mensajeAEnviar = getIdNombreEventosEncontrados(eventsResponse.getEvents(), mensajeAEnviar);
        }

        return mensajeAEnviar;
    }

    public void crearAlarma(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate,Optional<BigInteger> page, long chatId) {

        SearchDTO searchDTO = new SearchDTO(keyword, categories, startDate, endDate);

        EventacsCommands.createAlarm(getAccessToken(chatId),getUserId(chatId),searchDTO);
    }

        public void mostrarEventos(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate,Optional<BigInteger> page, long chatId){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        EventsResponse eventsResponse = EventacsCommands.getEvents(getAccessToken(chatId), keyword, categories, startDate, endDate, page);

        if(eventsResponse.getEvents().isEmpty()){
            mensajeAEnviar.append("No se encontraron eventos");
            enviarMensaje(mensajeAEnviar,chatId);
        }
        else{
            eventsResponse.getEvents().forEach(event -> mostrarUnEvento(event, chatId));
        }
    }

    public void mostrarUnEvento(Event e, long chatId){
        StringBuilder mensajeAEnviar = new StringBuilder();
        mensajeAEnviar.append("ID: /").append(e.getId()).append("\n");
        mensajeAEnviar.append("Nombre: ").append(e.getName()).append("\n\n");
        enviarMensaje(mensajeAEnviar, chatId);
    }

    public static void guardarToken(long key, GetAccessToken value) {
        telegramUsersRepository.save(key,value);
    }



    public String getUserId(long chatId){
        return telegramUsersRepository.findUserIdByChatId(chatId);
    }

    public boolean existeUserConChatID(long chatId) {
        return !Objects.isNull(getAccessToken(chatId));
    }

    public StringBuilder categoriasDisponibles(long chatId){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        List<Category> listaCategorias = EventacsCommands.getCategories(getAccessToken(chatId));

        if(listaCategorias.isEmpty()){
            mensajeAEnviar.append("No se encontraron categorias");
        }
        else{
            mensajeAEnviar = getIdNombreCategoriasEncontradas(listaCategorias, mensajeAEnviar);
        }

        return mensajeAEnviar;
    }

    public StringBuilder listasDeEventosDisponibles(long chatId){

        StringBuilder mensajeAEnviar = new StringBuilder ();
        List<EventList> listasDeEventos = eventService.getEventLists(getUserId(chatId));

        if(listasDeEventos.isEmpty()){
            mensajeAEnviar.append("No se encontraron listas de eventos\nIngrese /cancelar para salir\n");
        }
        else{
            mensajeAEnviar = getIdNombreListasEncontradas(listasDeEventos, mensajeAEnviar);
        }

        return mensajeAEnviar;
    }

    private StringBuilder getIdNombreListasEncontradas(List<EventList> listasDeEventos, StringBuilder mensajeAEnviar) {
        mensajeAEnviar.append("Listas Disponibles:\n");
        StringBuilder finalMensajeAEnviar = mensajeAEnviar;
        listasDeEventos.forEach(e -> agregarLista(e, finalMensajeAEnviar));
        mensajeAEnviar = finalMensajeAEnviar;
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
        mensajeAEnviar.append("ID: /").append(e.getId()).append("\n");
        mensajeAEnviar.append("Nombre: ").append(e.getName()).append("\n\n");
    }

    private void agregarLista(EventList lista, StringBuilder mensajeAEnviar) {
        mensajeAEnviar.append("ID: /").append(lista.getListId()).append("\n");
        mensajeAEnviar.append("Nombre: ").append(lista.getListName()).append("\n\n");
    }

    public void crearLista(String nombreLista, long chatId) {
        EventListCreationDTO eventList = new EventListCreationDTO(getUserId(chatId), nombreLista);
        EventacsCommands.createEventList(getAccessToken(chatId), eventList);
        //eventService.createEventList(eventList);
    }

    public void cambiarNombreLista(String listId, String newName, long chatId) {
        //EventListCreationDTO eventList = new EventListCreationDTO(getUserId(chatId), nombreLista);
        EventacsCommands.changeListName(getAccessToken(chatId), listId, new ListName(newName));
        //eventService.changeListName(listId, newName);
    }

    public void eliminarLista(String listaId, long chatId) {
        EventacsCommands.deleteEventList(getAccessToken(chatId),listaId);
    }
}