package com.eventacs.external.telegram.client;

import com.eventacs.external.eventbrite.model.GetAccessToken;
import com.eventacs.external.telegram.client.httprequest.EventacsCommands;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Validaciones {

    /*
    Clase para realizar las validaciones de los datos ingresados.
    En caso de que el dato no sea válido, se retorna false y se detalla el error en mensajeDeError
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TacsBot.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean categoriaValida(String idCategoria, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean fechainicioValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    //va a validar tambien que la fecha de fin sea posterior o igual a la de inicio
    public static boolean fechafinValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean idListaValida(String part, StringBuilder mensajeDeError) {
        return true;
    }

    public static boolean idEventoValido(String part, StringBuilder mensajeDeError) {
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
}
