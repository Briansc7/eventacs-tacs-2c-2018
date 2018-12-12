package com.eventacs.external.telegram.client;

import com.eventacs.event.model.Category;
import com.eventacs.event.model.EventList;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.httprequest.EventacsCommands;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class Validaciones {

    /*
    Clase para realizar las validaciones de los datos ingresados.
    En caso de que el dato no sea válido, se retorna false y se detalla el error en mensajeDeError
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean categoriaValida(String idCategoria, StringBuilder mensajeDeError, String token) {
        List<Category> categorias = EventacsCommands.getCategories(token);

        mensajeDeError.append("El id de categoría ingresado no existe");

        return categorias.stream().anyMatch(c->c.getId().equalsIgnoreCase(idCategoria));
    }

    public static  boolean fechaValida(String fecha){
        //formato de fecha aaaa-mm-dd
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;

    }

    public static boolean fechainicioValida(String part, StringBuilder mensajeDeError) {
        mensajeDeError.append("La fecha ingresada no es válida");
        return fechaValida(part);
    }

    //va a validar tambien que la fecha de fin sea posterior o igual a la de inicio
    public static boolean fechafinValida(String fechaFinString, LocalDate fechaInicio, StringBuilder mensajeDeError) {

        if(fechaValida(fechaFinString)){
            LocalDate fechaFin = LocalDate.parse(fechaFinString);

            if(fechaInicio.isAfter(fechaFin)){
                mensajeDeError.append("La fecha de fin debe ser posterior a la fecha de inicio");
                return false;
            }

        }
        else{
            mensajeDeError.append("La fecha ingresada no es válida");
            return false;
        }

        return true;


    }

    public static boolean idListaValida(String listId, StringBuilder mensajeDeError, String token, String userId, TacsBot tacsBot) {

        /*if(EventacsCommands.getEventList(token, listId) == null){
            mensajeDeError.append("El id de lista ingresado no existe");
            return false;
        }
        else
            return true;*/
        //TODO usar EventacsCommands y crear un recurso para obtenerlo por Get a /event-lists (problema como obtener el userId)
        EventService eventService = tacsBot.getEventService();
        List<EventList> listas = eventService.getEventLists(userId);

        mensajeDeError.append("El id de lista ingresado no es válido");

        return listas.stream().anyMatch(l->l.getListId().equals(Long.valueOf(listId)));

    }

    //validado de otra forma
    public static boolean idEventoValido(String eventId, StringBuilder mensajeDeError, String token) {
        if(EventacsCommands.getEvent(token, eventId) == null){
            mensajeDeError.append("El id de evento ingresado no existe");
            return false;
        }
        else
            return true;
    }

    public static GetAccessToken userPwValido(String username, String pw)  {
        return EventacsCommands.login(username, pw);
    }

    public static boolean usuarioVerificado(long chatId, TacsBot tacsBot) {
        return tacsBot.existeUserConChatID(chatId);
    }

    public static String formatearId(String id){
        char firstCharacter = id.charAt(0);

        if (firstCharacter == '/')
            return id.substring(1);
        else
            return id;
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
